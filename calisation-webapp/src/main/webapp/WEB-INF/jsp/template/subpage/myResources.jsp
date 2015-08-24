<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<script type="text/javascript">
    var initialVideoClips = <c:out value="${videoClips}" escapeXml="false"/>;
    var initialAudioClips = <c:out value="${audioClips}" escapeXml="false"/>;
    var initialSelectedAudioClips = [];
    for(var i = 0; i<initialVideoClips.length; i++) {
        initialSelectedAudioClips.push({});
    };
</script>

<div ng-controller="ClipsController">
    <div flash-message="5000"></div>
    <div ng-controller="ClipsTabController">

        <ul class="nav nav-pills">
            <li ng-class="{active:isTabSelected('video')}">
                <a ng-click="selectTab('video')">Video</a>
            </li>
            <li ng-class="{active:isTabSelected('audio')}">
                <a ng-click="selectTab('audio')">Audio</a>
            </li>
        </ul>
        <div id="videoClips" ng-show="isTabSelected('video')">
            <div>
                <input type="number" min="0.0" step="0.1" ng-model="start"/>
                <input type="number" min="0.0" step="0.1" ng-model="end"/>
                <input ng-model="withAudio" type="checkbox" checked/>
                <input type="text" ng-model="name"/>
                <input type="file" ngf-select ng-model="file" ngf-multiple="false"/>
                <input type="submit" ng-click="uploadClip()"/>
            </div>
            <div>
                <div ng-repeat="videoClip in videoClips">
                    <video controls style="max-width: 23%; float: left; margin: 1%">
                        <source ng-src="{{videoClip.url | trusted}}"/>
                    </video>
                    <div>
                        <button ng-click="{deleteClip({{videoClip.url}})}">Delete</button>
                    </div>
                </div>
                <br style="clear: left;" />
            </div>
        </div>
        <div id="audioClips" ng-show="isTabSelected('audio')">
            <div>
                <input type="number" min="0" step="0.1" ng-model="start"/>
                <input type="number" min="0" step="0.1" ng-model="end"/>
                <input type="text" ng-model="name"/>
                <input type="file" ngf-select ng-model="file" ngf-multiple="false"/>
                <input type="submit" ng-click="uploadClip()"/>
            </div>
            <div>
                <div ng-repeat="audioClip in audioClips">
                    <audio controls style="max-width: 23%; float: left; margin: 1%">
                        <source ng-src="{{audioClip.url | trusted}}"/>
                    </audio>
                    <div>
                        <button ng-click="{deleteClip({{audioClip.url}})}">Delete</button>
                    </div>
                </div>
                <br style="clear: left;" />
            </div>
        </div>
    </div>
    <button ng-click="openVideoMergingDialog()">Scal</button>
    <button id="toggleSequenceButton" ng-click="toggleSequence()">Uruchom sekwencję</button>

</div>
<div>
    <form:form method="post" action="uploadResource" commandName="resource" enctype="multipart/form-data">
        <div>
            <div><form:label path="name">Name of your file:</form:label></div>
            <div>
                <span><form:input path="name"></form:input></span>
                <span><form:errors path="name"></form:errors></span>
            </div>
        </div>
        <div>
            <div><form:label path="file">File to upload:</form:label></div>
            <div>
                <span><form:input type="file" path="file"></form:input></span>
                <span><form:errors path="file"></form:errors></span>
            </div>
        </div>
        <input type="submit" value="Upload"> Press here to upload the file!
    </form:form>
</div>

<script src="/js/jquery.js"></script>
<script src="/js/jquery-ui.js"></script>
<script src="/js/angular.js" type="text/javascript"></script>
<script src="/js/ng-file-upload-all.min.js"></script>
<script src="/js/ngProgress.min.js"></script>
<script src="/js/angular-flash.js"></script>
<script src="/js/ngDialog.js"></script>

<script src="/js/angular-dragdrop.min.js"></script>
<script src="/js/clips.js"></script>

<article class="article">
	<spring:message code="about.about"/>
</article>
