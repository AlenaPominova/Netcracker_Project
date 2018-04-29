<!DOCTYPE html>

<head>
    <title>Parking</title>
    <#setting locale="en">
    <#--<#setting number_format="0.######">-->
    <style>
        form {
            display: inline-block;
        }
        #map {
            height: 400px;
            width: 100%;
            background-color: grey;
        }
    </style>
    <#assign url = springMacroRequestContext.getPathToServlet() >
${url!}
</head>
<body>
<table border="1" cellspacing="0" bgcolor="#eeeeee">
    <tr>
        <td width="200">Id:</td>
        <td width="250">${parking.id}</td>
    </tr>
    <tr>
        <td>Парковка:</td>
        <td>${parking.name}</td>
    </tr>
    <tr>
        <td>OwnerId:</td>
        <td>${parking.references?api.get(300?long)?string}</td>
    </tr>
    <tr>
        <td>Address:</td>
        <td>${parking.values?api.get(303?long)}</td>
    </tr>
    <tr>
        <td>Latitude:</td>
        <td>${parking.values?api.get(301?long)}</td>
    <tr>
        <td>Longitude:</td>
        <td>${parking.values?api.get(302?long)}</td>
    </tr>
    <tr>
        <td>Price $ / hour:</td>
        <td>${parking.values?api.get(304?long)}</td>
    </tr>
    <tr>
        <td>Free spots count:</td>
        <td>${parking.values?api.get(307?long)}</td>
    </tr>
    <tr>
        <td> Avaliability time:</td>
        <td>${parking.dateValues?api.get(305?long)?time} - ${parking.dateValues?api.get(306?long)?time}</td>
    </tr>
    <tr>
        <td>Status:</td>
        <td>${parking.listValues?api.get(308?long)}</td>
    </tr>
    <tr>
        <td>Rating:</td>
        <td>${parking.values?api.get(100?long)?number}</td>
    </tr>


</table>

<form method="post" action="${url}/evac/send-request/${parking.id}">
    <input type="submit" value="Send Evac Request" />
</form>

    <#--<form method="get" action="${url}/parkings/${parking.id}/edit">-->
        <#--<input type="hidden" name = "Id" value="${parking.id}">-->
        <#--<input type="hidden" name = "Name" value="${parking.name}">-->
        <#--<input type="hidden" name = "ownerId" value="${parking.ownerId}">-->
        <#--<input type="hidden" name = "latitude" value="${parking.latitude}">-->
        <#--<input type="hidden" name = "longitude" value="${parking.longitude}">-->
        <#--<input type="hidden" name = "parkingAddress" value="${parking.parkingAddress}">-->
        <#--<input type="hidden" name = "price" value="${parking.price}">-->
        <#--<input type="hidden" name = "openTime" value="${parking.openTime}">-->
        <#--<input type="hidden" name = "closeTime" value="${parking.closeTime}">-->
        <#--<input type="hidden" name = "freeSpotsCount" value="${parking.freeSpotsCount!}">-->
        <#--<input type="hidden" name = "status" value="${parking.status}">-->
        <#--<input type="hidden" name = "rating" value="${parking.rating?number}">-->
        <#--<input type="submit" value="Edit" />-->
    <#--</form>-->

    <#--<form method="post" action="${url}/parkings/${parking.id}">-->
        <#--<input type="hidden" name="_method" value="delete"/>-->
        <#--<input type="submit" value="Delete" />-->
    <#--</form>-->
</body>
</html>