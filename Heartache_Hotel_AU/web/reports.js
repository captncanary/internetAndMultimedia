
$(document).ready(function () {
   /* set date picker value to todays date */
   var date = new Date();
   var day = ("0" + date.getDate()).slice(-2);
   var month = ("0" + (date.getMonth() + 1)).slice(-2);
   var today = date.getFullYear() + "-" + (month) + "-" + (day);
   $('#fdate').val(today);
});