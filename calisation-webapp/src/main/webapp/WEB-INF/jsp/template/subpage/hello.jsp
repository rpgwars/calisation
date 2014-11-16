
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!--#map-canvas { height: 100vh; width: 100%; }-->
<style type="text/css">
      #map-canvas { width: 100%; }  
      img {max-width: none;}
</style>

<script type="text/javascript"
   src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA4afADtLKKfVMvPf1nmUhsyqOZpgETd1M">
</script>
<script type="text/javascript">
      function initialize() {
        var mapOptions = {
          center: new google.maps.LatLng("${centerNotification.latitude}", "${centerNotification.longtitude}"),
          zoom: ${zoom},
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById("map-canvas"),
            mapOptions);
        
		
        
        var positionNotifications = new Array();
        
        <c:forEach items="${positionNotificationList}" var="notification">
        	var location = {latitude: ${notification.latitude}, longtitude: ${notification.longtitude}, description: "${notification.description}"};
        	positionNotifications.push(location);
        </c:forEach>
        
        
        for(i = positionNotifications.length - 1; i>-1; i--){
        	marker = new google.maps.Marker({
        		map : map,
        		position: new google.maps.LatLng(positionNotifications[i].latitude, positionNotifications[i].longtitude),
        		title: positionNotifications[i].description
        	});
        	infowindow = new google.maps.InfoWindow({
        		position: new google.maps.LatLng(positionNotifications[i].latitude, positionNotifications[i].longtitude),
        		content: positionNotifications[i].description
        	});
        	google.maps.event.addListener(marker, 'click', function(currentMarker, currentInfowindow){
        		return function() {
        			currentInfowindow.open(map, currentMarker);
        		}
        	}(marker, infowindow));
        	
        }
        document.getElementById("map-canvas").style.height =
			Math.max(document.documentElement.clientHeight, window.innerHeight || 0) + 'px';
      }
      google.maps.event.addDomListener(window, 'load', initialize);
</script>
<script type="text/javascript">
function getLocation(){
	return document.getElementById("code").value;
}
</script>
<div>
<spring:message code="map.enterCode"/><br/><input type="text" id="code" name="code"/>
<button title="ok" onClick="parent.location=getLocation()">OK</button>
<c:if test="${noSuchCode}">
<spring:message code="map.noSuchCode"/>
</c:if>
</div>
<div id="map-canvas">
</div>

