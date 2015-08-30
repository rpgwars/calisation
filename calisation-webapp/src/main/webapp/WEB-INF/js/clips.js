document.getElementsByTagName('body').item(0).setAttribute('ng-app','clips')

var clips = angular.module("clips", ['ngFileUpload', 'ngProgress', 'flash', 'ngDialog', 'ngDragDrop']);
var maxClipsNumberAllowed = 100;

clips.factory('videoClips', function(){
    return initialVideoClips;
});

clips.factory('audioClips', function(){
    return initialAudioClips
});

clips.factory('selectedAudioClips', function(){
    return initialSelectedAudioClips;
});


clips.filter('trusted', ['$sce', function ($sce) {
    return function(url) {
        return $sce.trustAsResourceUrl(url);
    };
}]);

clips.controller('ClipsTabController', ['$scope', function($scope){
    $scope.selectedTab = 'audio';

    $scope.selectTab = function(selectedTab){
        $scope.selectedTab = selectedTab;
    };

    $scope.isTabSelected = function(tab){
        return $scope.selectedTab === tab;
    };

}]);

//https://github.com/danialfarid/ng-file-upload
clips.controller('ClipsController', ['$scope', 'Upload', '$http', 'ngProgressFactory', 'Flash', 'ngDialog', 'videoClips', 'audioClips', 'selectedAudioClips',
    function ($scope, Upload, $http, ngProgressFactory, Flash, ngDialog, videoClips, audioClips, selectedAudioClips) {

    $scope.audioClips = audioClips;
    $scope.videoClips = videoClips;

    $scope.withAudio = true;

    function refreshClips(response){
        audioClips.length = 0;
        videoClips.length = 0;
        selectedAudioClips.length = 0;
        angular.forEach(response, function(val, key){
            if(val.resourceType == 'AUDIO'){
                audioClips.push(val);
            }
            if(val.resourceType == 'VIDEO'){
                videoClips.push(val);
                selectedAudioClips.push([]);
            }
        });
    }

    $scope.deleteClip = function(url) {
        $http.delete('/myResources/deleteClip?url=' + url).
            then(function(response) {
                refreshClips(response.data);
            }, function(response) {
                alert('Wystąpił problem w trakcie usuwania klipu :( ');
            });
    };

    $scope.uploadClip = function () {

        if (this.file) {
            if(this.start >= this.end) {
               alert("Niepoprawny zakres początku i końca klipu");
            }
            else if(videoClips.length + audioClips.length > maxClipsNumberAllowed) {
               alert("Możesz posiadać co najwyżej " + maxClipsNumberAllowed + " klipów. Usuń któryś przed dodaniem następnego.");
            }
            else{
                /*$timeout(function() {
                 alert('Swerwer zbyt dłgo nie odpowiada ' + JSON.stringify(data));
                 $scope.progressbar.reset();
                 });*/
                $scope.progressbar = ngProgressFactory.createInstance();
                $scope.progressbar.start();
                Upload.upload({
                    url: '/myResources/addClip',
                    fields: {
                        'start': this.start,
                        'end': this.end,
                        'withAudio': this.withAudio,
                        'name': this.name
                    },
                    file: this.file
                }).progress(function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    $scope.progressbar.set(progressPercentage);
                    if(evt.loaded == evt.total)
                        $scope.successAlert();
                }).success(function (data, status, headers, config) {
                    refreshClips(data);
                }).error(function (data, status, headers, config) {
                    if(status == 403){
                        alert('Przekroczyłeś maksymalną liczbę klipów na koncie. Usuń jeden z nich przed dodaniem następnego.');
                    }
                    else {
                        console.log('error status: ' + status);
                        alert('Wystąpił błąd w trakcie tworzenia klipu :(');
                    }
                    $scope.progressbar.reset();
                });
            }
        }
    };

    $scope.combineClips = function() {
        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", "/myResources");
        var clipsCombination = {
            videoClips : $scope.videoClips,
            selectedAudioClips : $scope.selectedAudioClips
        };

        var clipsCombinationField = document.createElement("input");
        clipsCombinationField.setAttribute("type", "hidden");
        clipsCombinationField.setAttribute("name", "clipsCombination");
        clipsCombinationField.setAttribute("value", JSON.stringify(clipsCombination));
        form.appendChild(clipsCombinationField);

        document.body.appendChild(form);
        form.submit();
    }

    $scope.successAlert = function () {
        var message = 'Plik został wysłany na serwer. Długość jego przetwarzanie będzie zależeć od formatu i rozmiaru klipu.';
        $scope.progressbar.reset();
        Flash.create('success', message);
    };

    $scope.openVideoMergingDialog = function () {
        document.getElementById("mainFlash").style.display = "none";
        ngDialog.open({
            template: '/partials/mergeClipsPartial.html',
            controller: 'videoMergingController',
            className: 'ngdialog-theme-default ngdialog-theme-custom'
        });
    };

    $scope.sequencePlaying = false;
    $scope.videoInSequenceNr = 0;
    $scope.audioInSequenceNr = 0;

    function rewindMedia(mediaTag, controls){
        var media = document.getElementsByTagName(mediaTag);
        for(var i = 0; i<media.length; i++) {
            if(controls)
                media.item(i).setAttribute("controls", "true");
            else
                media.item(i).removeAttribute("controls");
            media.item(i).pause();
            media.item(i).currentTime = 0;
        }
    }

    function rewindAllMedia(controls){
        rewindMedia('audio',controls);
        rewindMedia('video',controls);
    }

    function stopSequence(){
        $scope.videoInSequenceNr = 0;
        $scope.sequencePlaying = false;
        document.getElementById("toggleSequenceButton").innerHTML = "Uruchom sekwencję";
        angular.forEach(document.getElementsByClassName("deleteButton"), function(button, key){
            button.style.display = null;
        });
    }

    function continueSequence(e){
        if($scope.sequencePlaying) {
            if ($scope.videoInSequenceNr < videoClips.length) {
                stopPlayingAudioSubsequence($scope.videoInSequenceNr-1);
                startPlayingAudioSubsequence($scope.videoInSequenceNr);
                document.getElementsByTagName('video').item($scope.videoInSequenceNr++).play();
            }
            else {
                stopPlayingAudioSubsequence($scope.videoInSequenceNr - 1);
                stopSequence();
            }
        }
    }

    function getAssociatedAudioClips(videoInSequenceNr){
        var audioElements = [];
        angular.forEach(selectedAudioClips[videoInSequenceNr], function(subSelectedAudioClip, key){
            audioElements.push(document.querySelectorAll("[src='" + subSelectedAudioClip.url + "']").item(0).parentNode);
        });
        return audioElements;
    }

    function startPlayingAudioSubsequence(videoInSequenceNr){
        var audioElements = getAssociatedAudioClips(videoInSequenceNr);
        angular.forEach(audioElements, function(audioElement, index){
            audioElement.addEventListener('ended', function(){
                if(index+1 < audioElements.length){
                    audioElements[index+1].play();
                }
            })
        });
        if(audioElements.length > 0)
            audioElements[0].play();
    }

    function stopPlayingAudioSubsequence(videoInSequenceNr){
        var audioElements = getAssociatedAudioClips(videoInSequenceNr);
        angular.forEach(audioElements, function(audioElement, index){
            audioElement.removeEventListener('ended');
            audioElement.pause();
            audioElement.currentTime = 0;
        });
    }

    $scope.toggleSequence = function(){
        if(!$scope.sequencePlaying){
            $scope.sequencePlaying = true;
            angular.forEach(document.getElementsByClassName("deleteButton"), function(button, key){
                button.style.display = "none";
            });
            document.getElementById("toggleSequenceButton").innerHTML = "Zatrzymaj";
            rewindAllMedia(false);
            var videos = document.getElementsByTagName('video');
            for(var i = 0; i<videos.length; i++) {
                videos.item(i).addEventListener('ended', continueSequence, false);
            }
            startPlayingAudioSubsequence($scope.videoInSequenceNr);
            document.getElementsByTagName('video').item($scope.videoInSequenceNr++).play();
        }
        else{
            stopSequence();
            rewindAllMedia(true);
        }
    }
}]);

//http://codef0rmer.github.io/angular-dragdrop/#/
clips.controller('videoMergingController', ['$scope', 'videoClips', 'selectedAudioClips', 'ngDialog', '$q', 'Flash',
    function($scope, videoClips, selectedAudioClips, ngDialog, $q, Flash) {

    $scope.videoClips = videoClips;

    $scope.openAudioMergingDialog = function () {
        ngDialog.close('ngdialog1');
        ngDialog.open({
            template: '/partials/mergeAudioPartial.html',
            controller : 'audioMergingController',
            className: 'ngdialog-theme-default ngdialog-theme-custom',
            preCloseCallback: function(value){
                document.getElementById("mainFlash").style.display = null;
                Flash.dismiss();
                return true;
            }
        });
    };

    $scope.startCallback = function(event, ui, title) {

    };

    var draggedIndex = -1;

    $scope.stopCallback = function(event, ui) {
        var droppedIndex = this.$index;
        if(draggedIndex != -1){
            var tmp = selectedAudioClips[draggedIndex];
            selectedAudioClips[draggedIndex] = selectedAudioClips[droppedIndex];
            selectedAudioClips[droppedIndex] = tmp;
            draggedIndex = -1;
        }
    };

    $scope.beforeDrop = function(){
        draggedIndex = this.$index;
        var deferred = $q.defer();
        deferred.resolve();
        return deferred.promise;
    };

    $scope.dragCallback = function(event, ui) {

    };

    $scope.dropCallback = function(event, ui) {

    };

    $scope.overCallback = function(event, ui) {

    };

    $scope.outCallback = function(event, ui) {

    };

}]);


clips.controller('audioMergingController', ['$scope', 'videoClips', 'audioClips', 'selectedAudioClips', 'Flash', '$q', function($scope, videoClips, audioClips, selectedAudioClips, Flash, $q) {
    $scope.videoClips = videoClips;
    $scope.audioClips = audioClips;
    $scope.audioClipsCopy = [];

    $scope.isAudioClipSelected = function(audioClip){
        for(i = 0; i< selectedAudioClips.length; i++){
            if(selectedAudioClips[i].indexOf(audioClip) != -1){
                return true;
            }
        }
        return false;
    };

    $scope.selectedAudioClips = selectedAudioClips;
    angular.forEach($scope.audioClips, function(audioClip, key) {
        if($scope.isAudioClipSelected(audioClip))
            $scope.audioClipsCopy.push({});
        else
            $scope.audioClipsCopy.push(audioClip);
    });

    $scope.totalDuration = 0;

    var videos = document.getElementsByTagName('video');
    for(var i = 0; i<videos.length; i++) {
        $scope.totalDuration += videos[i].duration;
    }

    $scope.clipsDurationPercentage = [];

    for(var i = 0; i<videos.length; i++) {
        $scope.clipsDurationPercentage.push(parseInt(100.0 * videos[i].duration / $scope.totalDuration) + "%");
    }

    $scope.startCallback = function(event, ui, title) {

    };

    $scope.stopCallback = function(event, ui) {

    };

    $scope.dragCallback = function(event, ui) {

    };

    $scope.dropCallback = function(event, ui) {

    };

    $scope.beforeDrop = function(){
        var deferred = $q.defer();
        if(this.video.withAudio == true) {
            deferred.reject();
        }else {
            deferred.resolve();
        }
        return deferred.promise;
    };

    $scope.overCallback = function(event, ui) {
        if(this.video.withAudio == true){
            var message = 'Ten klip ma wybraną oryginalną ścieżkę dzwiękową';
            Flash.create('info', message);
        }
    };

    $scope.outCallback = function(event, ui) {
        Flash.dismiss();
    };
}]);

