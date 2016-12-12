/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global index */

$(document).ready(function () {
   updateForNoRooms();
   refreshCheckboxValue(document.getElementById('cot1'));
   refreshCheckboxValue(document.getElementById('cot2'));
   refreshCheckboxValue(document.getElementById('cot3'));
   refreshCheckboxValue(document.getElementById('cot4'));

   /* set date picker value to todays date */
   var date = new Date();
   var day = ("0" + date.getDate()).slice(-2);
   var month = ("0" + (date.getMonth() + 1)).slice(-2);
   var today = date.getFullYear() + "-" + (month) + "-" + (day);
   $('#fdate').val(today);

   /* update last modified date*/
   /* document.getElementById('footerText').innerHTML = 'Page last updated '
    + fetchHeader(location.href, 'Last-Modified') + ' by Team A';*/
   initMap();
});


	
function initMap() {
  var myLatLng = {lat: 52.656879, lng: 1.194937};

  var map = new google.maps.Map(document.getElementById('map-canvas'), {
    zoom: 10,
    center: myLatLng
  });

  var marker = new google.maps.Marker({
    position: myLatLng,
    map: map,
    title: 'Heartache Hotel'
  });
}



function setPreviewImage(roomtype)
{
   // to get the value of room type
   var roomType = document.getElementById(roomtype).value;
   if (roomType === "stdtwin")
   {
	document["preview"].src = "images/standard_twin.jpg";
	document.getElementById('previewcaption').innerHTML = 'Standard Twin Room';
   } else if (roomType === "stddouble")
   {
	document["preview"].src = "images/standard_double.jpg";
	document.getElementById('previewcaption').innerHTML = 'Standard Double Room';
   } else if (roomType === "dlxtwin")
   {
	document["preview"].src = "images/deluxe_twin.jpg";
	document.getElementById('previewcaption').innerHTML = 'Deluxe Twin Room';
   } else if (roomType === "dlxdouble")
   {
	document["preview"].src = "images/deluxe_double.jpg";
	document.getElementById('previewcaption').innerHTML = 'Deluxe Double Room';
   }
}

function setCaption(imageName)
{
   if (imageName === "stdtwin")
   {
	document["preview"].src = "images/standard_twin.jpg";
	document.getElementById('previewcaption').innerHTML = 'Standard Twin Room';
   } else if (imageName === "stddouble")
   {
	document["preview"].src = "images/standard_double.jpg";
	document.getElementById('previewcaption').innerHTML = 'Standard Double Room';
   } else if (imageName === "dlxtwin")
   {
	document["preview"].src = "images/deluxe_twin.jpg";
	document.getElementById('previewcaption').innerHTML = 'Deluxe Twin Room';
   } else if (imageName === "dlxdouble")
   {
	document["preview"].src = "images/deluxe_double.jpg";
	document.getElementById('previewcaption').innerHTML = 'Deluxe Double Room';
   } else if (imageName === "pool")
   {
	document["preview"].src = "images/pool.jpg";
	document.getElementById('previewcaption').innerHTML = 'Hotel Pool';
   } else if (imageName === "gym")
   {
	document["preview"].src = "images/gym.jpg";
	document.getElementById('previewcaption').innerHTML = 'Hotel Gym';
   }
}



// Legacy code - may not need this method
function getAndShowData()
{
   // to get the value of a data element, e.g. firstname
   var firstName = document.getElementById("fname").value;

   // to get the value of a data element, e.g. firstname
   var emailAddress = (String)(document.getElementById("eml").value);
   //alert(emailAddress.length);

   if (emailAddress.length > 5 && emailAddress.indexOf("@") !== null && emailAddress.indexOf(".") !== null)
   {
	document.getElementById("dataDemo").innerHTML = emailAddress;
   } else
   {
	alert('Enter Valid Email Address!');
   }

   // to get the value of all the data elements on the form,
   // use “for” loop to go over the index of the elements.
   var x = document.getElementById("nameForm");
   var text = "";
   var i;
   for (i = 0; i < x.length - 2; i++) {
	if ((String)(x.elements[i].value) !== "undefined")
	{
	   //alert(x.elements[i].value);
	   text += x.elements[i].value + "<br>";
	}
   }
   document.getElementById("dataDemo").innerHTML = text;

}

function updateForNoRooms()
{
   var dropdown = document.getElementById("rooms");
   var current_value = dropdown.options[dropdown.selectedIndex].value;
   current_value = parseInt(current_value);

   var nightsDropdown = document.getElementById("nights");
   var nights_value = nightsDropdown.options[nightsDropdown.selectedIndex].value;
   nights_value = parseInt(nights_value);

   if (current_value === 1) {
	$("#submit").show();
	document.getElementById("room1").style.display = "inline-block";
	$("#room2").hide();
	$("#room3").hide();
	$("#room4").hide();
	$("#room5").hide();
   } else if (current_value === 2) {
	$("#submit").show();
	document.getElementById("room1").style.display = "inline-block";
	document.getElementById("room2").style.display = "inline-block";
	$("#room3").hide();
	$("#room4").hide();
	$("#room5").hide();
   } else if (current_value === 3) {
	$("#submit").show();
	document.getElementById("room1").style.display = "inline-block";
	document.getElementById("room2").style.display = "inline-block";
	document.getElementById("room3").style.display = "inline-block";
	$("#room4").hide();
	$("#room5").hide();
   } else if (current_value === 4) {
	$("#submit").show();
	document.getElementById("room1").style.display = "inline-block";
	document.getElementById("room2").style.display = "inline-block";
	document.getElementById("room3").style.display = "inline-block";
	document.getElementById("room4").style.display = "inline-block";
	$("#room5").hide();
   } else if (current_value === 5) {
	$("#submit").hide();
	$("#room1").hide();
	$("#room2").hide();
	$("#room3").hide();
	$("#room4").hide();
	$("#room5").show();

   }

   if (nights_value === 15) {
	$("#submit").hide();
	$("#room1").hide();
	$("#room2").hide();
	$("#room3").hide();
	$("#room4").hide();
	$("#night15").show();
   } else
   {
	$("#night15").hide();
   }

}

function refreshCheckboxValue(checkbox) {
   checkbox.value = checkbox.checked;
}


//http://stackoverflow.com/questions/2313620/is-it-possible-to-retrieve-the-last-modified-date-of-a-file-using-javascript
function fetchHeader(url, wch)
{
   try
   {
	var req = new XMLHttpRequest();
	req.open("HEAD", url, false);
	req.send(null);
	if (req.status == 200) {
	   return req.getResponseHeader(wch);
	} else
	   return false;
   } catch (er)
   {
	return er.message;
   }
}
