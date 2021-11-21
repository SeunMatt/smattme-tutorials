<html>
<head>
    <title>Custom Application Error Page</title>
</head>
<body>
    <h1>A custom application error</h1>
    <p>Message: ${message}</p>
    <p>Code: ${code}</p>
    <p>Errors</p>
    <ul>
        <#list errors as error>
            <li>${error}</li>
        </#list>
    </ul>
</body>
</html>