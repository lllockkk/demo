<#macro greet person>
    <h1>Welcome ${person}!</h1>
</#macro>

<html>
<head>
    <title>Welcome!</title>
</head>
<body>
<@greet user />
<p>Our latest product:
    <a href="${latestProduct.url}">${latestProduct.name}</a>!
</body>
</html>