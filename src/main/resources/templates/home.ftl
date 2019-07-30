<html>
<head>
    <title>Чат</title>
</head>
<body>
    <#list image as images>
        <label>${images.getDate()}</label>
        <br>
        <img src="${images.getUrl()}" width="300" height="300">
        <br>
    </#list>
</body>
</html>