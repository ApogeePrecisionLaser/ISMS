$(function(){
var apiURL="http://localhost:8081/ContactLess_Management/rest/apiServices/liveDashData";
var api_url1=apiURL;
var total_vehicles=[];
var vehicles_active=[];
var vehicles_inactive=[];
$.ajax({
	url: api_url1,
	contentType: "application/json",
	dataType: 'json',
	success: function(result){
		console.log(result);				
		$('#total_student').text(result.res);
	}
});

});

var markers = [];
var waypoints = [];
var map;
var map2;
var start;
var end;

var directionsService = new google.maps.DirectionsService();

function initialize(type,lat,long,name) {// debugger;
		var Apogee = {
				info:
					'<strong>Apogee Precision Laser</strong><br>\
					Sec 63 Noida <br> Noida, UP <br>\
					<a href="https://goo.gl/maps/jKNEDz4SyyH2">Get Directions</a>',
					lat: 28.6277622,
					long: 77.3766041,
		}

		var Indian = {
				info:
					'<strong>Indian Oil Petrol Pump</strong><br>\
					Sec 63 Noida <br> Noida, UP <br>\
					<a href="https://goo.gl/maps/jKNEDz4SyyH2">Get Directions</a>',
					lat: 28.6253416,
					long: 77.3744618,
		}

		var Pinnacle = {
				info:
					'<strong>Pinnacle</strong><br>\
					Sec 63 Noida <br> Noida, UP <br>\
					<a href="https://goo.gl/maps/jKNEDz4SyyH2">Get Directions</a>',
					lat: 28.6246263,
					long: 77.3721153,
		}

		var mapOptions = {
				zoom: 15,
				center: new google.maps.LatLng(28.6277622, 77.3766041),
				travelMode: google.maps.TravelMode.DRIVING

		};

		var locations = [
			[Apogee.info, Apogee.lat, Apogee.long, 0],
			[Indian.info, Indian.lat, Indian.long, 1],
			[Pinnacle.info, Pinnacle.lat, Pinnacle.long, 2],
			]

		var fire_station=[
			[Apogee.info, Apogee.lat, Apogee.long, 0]
			]


		map = new google.maps.Map(document.getElementById('map'), mapOptions);						
		
		// var locations = array;

		var infowindow = new google.maps.InfoWindow({})

		var marker, i
		var image="image/firedept.png";


		if(type=='yes'){
			// for all lat long
			for (i = 0; i < fire_station.length; i++) {
				marker = new google.maps.Marker({
					position: new google.maps.LatLng(locations[i][1], locations[i][2]),
					map: map,
					icon: image,
				})

				google.maps.event.addListener(
						marker,
						'click',
						(function (marker, i) {
							return function () {
								infowindow.setContent(locations[i][0])
								infowindow.open(map, marker)
							}
						})(marker, i)
				)
			}
			// end for all lat long
		}else if(type=='fire_stationNew'){
			// for all lat long
			for (i = 0; i < locations.length; i++) {
				marker = new google.maps.Marker({
					position: new google.maps.LatLng(locations[i][1], locations[i][2]),
					map: map,
					icon: image,
				})

				google.maps.event.addListener(
						marker,
						'click',
						(function (marker, i) {
							return function () {
								infowindow.setContent(locations[i][0])
								infowindow.open(map, marker)
							}
						})(marker, i)
				)
			}
			// end for all lat long
		}else if(type=='fuel_station'){
			$('#div_fuel_station_map').show();

			var fuel_station = {
					info:
						'<strong>'+name+'</strong><br>\
						<br>\
						<a href="">Get Directions</a>',
						lat: lat,
						long: long,
			}

			var mapOptions = {
					zoom: 15,
					center: new google.maps.LatLng(lat, long),
					travelMode: google.maps.TravelMode.DRIVING
			};

			var locations = [
				[fuel_station.info, fuel_station.lat, fuel_station.long, 0],
				]

			map = new google.maps.Map(document.getElementById('map'), mapOptions);

			for (i = 0; i < locations.length; i++) {
				marker = new google.maps.Marker({
					position: new google.maps.LatLng(locations[i][1], locations[i][2]),
					map: map,
					icon: image,
				})

				google.maps.event.addListener(
						marker,
						'click',
						(function (marker, i) {
							return function () {
								infowindow.setContent(locations[i][0])
								infowindow.open(map, marker)
							}
						})(marker, i)
				)
			}
		}else if(type=='vehicle_map'){

			$('#div_fuel_vehicle_map').show();

			var fuel_station = {
					info:
						'<strong>Vehicle</strong><br>\
						<br>\
						<a href="">Get Directions</a>',
						lat: 28.6277622,
						long: 77.3766041,
			}

			var mapOptions = {
					zoom: 15,
					center: new google.maps.LatLng(28.6277622, 77.3766041),
					travelMode: google.maps.TravelMode.DRIVING
			};

			var locations = [
				[fuel_station.info, fuel_station.lat, fuel_station.long, 0],
				]

			map = new google.maps.Map(document.getElementById('map'), mapOptions);

			for (i = 0; i < locations.length; i++) {
				marker = new google.maps.Marker({
					position: new google.maps.LatLng(locations[i][1], locations[i][2]),
					map: map,
					icon: image,
				})

				google.maps.event.addListener(
						marker,
						'click',
						(function (marker, i) {
							return function () {
								infowindow.setContent(locations[i][0])
								infowindow.open(map, marker)
							}
						})(marker, i)
				)
			}
		}else if(type=='fire_station'){

			$('#div_fire_station_map').show();

			var fuel_station = {
					info:
						'<strong>Vehicle</strong><br>\
						<br>\
						<a href="">Get Directions</a>',
						lat: 28.6277622,
						long: 77.3766041,
			}

			var mapOptions = {
					zoom: 15,
					center: new google.maps.LatLng(28.6277622, 77.3766041),
					travelMode: google.maps.TravelMode.DRIVING
			};

			var locations = [
				[fuel_station.info, fuel_station.lat, fuel_station.long, 0],
				]

			map = new google.maps.Map(document.getElementById('map'), mapOptions);

			for (i = 0; i < locations.length; i++) {
				marker = new google.maps.Marker({
					position: new google.maps.LatLng(locations[i][1], locations[i][2]),
					map: map,
					icon: image,
				})

				google.maps.event.addListener(
						marker,
						'click',
						(function (marker, i) {
							return function () {
								infowindow.setContent(locations[i][0])
								infowindow.open(map, marker)
							}
						})(marker, i)
				)
			}
		}
		
		if(type=='fuel_station'){debugger;
			$('#div_fuel_station_map').show();

			var fuel_station = {
					info:
						'<strong>'+name+'</strong><br>\
						<br>\
						<a href="">Get Directions</a>',
						lat: lat,
						long: long,
			}

			var mapOptions = {
					zoom: 15,
					center: new google.maps.LatLng(lat, long),
					travelMode: google.maps.TravelMode.DRIVING
			};

			var locations = [
				[Apogee.info, Apogee.lat, Apogee.long, 0],
				[Indian.info, Indian.lat, Indian.long, 1],
				[Pinnacle.info, Pinnacle.lat, Pinnacle.long, 2],
				]

			map = new google.maps.Map(document.getElementById('map'), mapOptions);

			for (i = 0; i < locations.length; i++) {
				marker = new google.maps.Marker({
					position: new google.maps.LatLng(locations[i][1], locations[i][2]),
					map: map,
					icon: image,
				})

				google.maps.event.addListener(
						marker,
						'click',
						(function (marker, i) {
							return function () {
								infowindow.setContent(locations[i][0])
								infowindow.open(map, marker)
							}
						})(marker, i)
				)
			}
		}
		
		// start for live vehicles
		if(type=='live_vehicle'){debugger;	
		var image="image/green-dot.png";
		/*
		 * var image = { url:
		 * "https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png",
		 * size: new google.maps.Size(20, 32), origin: new google.maps.Point(0,
		 * 0), anchor: new google.maps.Point(0, 32) };
		 */
			var live_vehicle = {
					info:
						'<strong>'+name+'</strong><br>\
						<br>\
						<a href="">Get Directions</a>',
						lat: lat,
						long: long,
			}

			var mapOptions = {
					zoom: 15,
					center: new google.maps.LatLng(lat, long),
					travelMode: google.maps.TravelMode.ROADMAP
			};

			var locations = [
				[Apogee.info, Apogee.lat, Apogee.long, 0]
				]

			map = new google.maps.Map(document.getElementById('map2'), mapOptions);

			for (i = 0; i < locations.length; i++) {
				marker = new google.maps.Marker({
					position: new google.maps.LatLng(lat, long),
					map: map,
					icon: image,
				})

				google.maps.event.addListener(
						marker,
						'click',
						(function (marker, i) {
							return function () {
								infowindow.setContent(locations[i][0])
								infowindow.open(map, marker)
							}
						})(marker, i)
				)
			}
		}
		// end for live vehicles
		
		// start for fire locations
		if(type=='fire_location'){debugger;	
		var image="image/green-dot.png";
		/*
		 * var image = { url:
		 * "https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png",
		 * size: new google.maps.Size(20, 32), origin: new google.maps.Point(0,
		 * 0), anchor: new google.maps.Point(0, 32) };
		 */
			var live_vehicle = {
					info:
						'<strong>'+name+'</strong><br>\
						<br>\
						<a href="">Get Directions</a>',
						lat: lat,
						long: long,
			}

			var mapOptions = {
					zoom: 15,
					center: new google.maps.LatLng(lat, long),
					travelMode: google.maps.TravelMode.ROADMAP
			};

			var locations = [
				[Apogee.info, Apogee.lat, Apogee.long, 0]
				]

			map = new google.maps.Map(document.getElementById('map2'), mapOptions);

			for (i = 0; i < locations.length; i++) {
				marker = new google.maps.Marker({
					position: new google.maps.LatLng(lat, long),
					map: map,
					icon: image,
				})

				google.maps.event.addListener(
						marker,
						'click',
						(function (marker, i) {
							return function () {
								infowindow.setContent(locations[i][0])
								infowindow.open(map, marker)
							}
						})(marker, i)
				)
			}
		}
		// end for fire location

	// }
}

google.maps.event.addDomListener(window, 'load', initialize);