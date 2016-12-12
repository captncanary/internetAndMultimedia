$(document).ready(function() {
    /* update last modified date*/
    var test = document.getElementById("b_ref").text;
    //alert(test);
    if(test==="99999")
    {
       // Booking failed
       document.getElementById("fieldset").innerHTML = "Booking FAILED. Please try again or call for assistance.";
    }
});
