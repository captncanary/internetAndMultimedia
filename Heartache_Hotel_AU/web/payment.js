/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global index */



function startpayment()
{
    e = document.getElementById("pTypes");
    var payType = e.options[e.selectedIndex].value;
    var cardNumber = document.getElementById("cardNo");
    var expiration = document.getElementById("exDate");
    var firstName = document.getElementById("fName");
    var lastName = document.getElementById("lName");
    var addressLine1 = document.getElementById("address1");
    var city = document.getElementById("city");
    var postCode = document.getElementById("pCode");
    var emailAddress = document.getElementById("email");
    
    saveData(payType,cardNumber,expiration,firstName,lastName,addressLine1,
             city, postCode, emailAddress);
    //window.alert(arrivedate + " " +nights +" "+ rooms +" "+ adults +" "+ children + " "+ cot + " " + roomtype);
    void(open('confirmation.html','_self'));   
}

function saveData(payType,cardNumber,expiration,firstName,lastName,
                  addressLine1,city, postCode, emailAddress) {
   var payment = {
       PaymentType: payType,
       CardNumber: cardNumber,
       ExpirationDate: expiration,
       FirstName: firstName,
       LastName: lastName,
       AddressOne: addressLine1,
       City: city,
       PostCode: postCode,
       EmailAddress: emailAddress
   };
   //converts to JSON string Object
   payment = JSON.stringify(payment);
   //creates a base-64 encoded ASCII string
   payment = btoa(payment);
   
   //save the encoded accout to web storage
   localStorage.setItem('_payment', payment);
}

function getAndShowData()
{
    // to get the value of a data element, e.g. firstname
    var firstName = document.getElementById("fname").value;
    
    // to get the value of a data element, e.g. firstname
    var emailAddress = (String)(document.getElementById("email").value);
    //alert(emailAddress.length);
    
    if(emailAddress.length > 5 && emailAddress.indexOf("@") !== null && emailAddress.indexOf(".") !== null)
    {
        document.getElementById("dataDemo").innerHTML = emailAddress;
    }
    else
    {
        alert('Enter Valid Email Address!');
    }
    
    // to get the value of all the data elements on the form,
    // use “for” loop to go over the index of the elements.
    var x = document.getElementById("paymentForm");
    var text = "";
    var i;
    for (i = 0; i < x.length-2; i++) {
        if((String)(x.elements[i].value) !== "undefined")
        {
            //alert(x.elements[i].value);
            text += x.elements[i].value + "<br>";
        }
    }
    document.getElementById("dataDemo").innerHTML = text;

}

//http://stackoverflow.com/questions/2313620/is-it-possible-to-retrieve-the-last-modified-date-of-a-file-using-javascript
function fetchHeader(url, wch) {
    try {
        var req=new XMLHttpRequest();
        req.open("HEAD", url, false);
        req.send(null);
        if(req.status== 200){
            return req.getResponseHeader(wch);
        }
        else return false;
    } catch(er) {
        return er.message;
    }
}
