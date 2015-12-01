<HTML>
<body>
<h1>Document ${doc_number}</h1>

<form action="/page/newdoc_commit" method="post">
<table border=1>

<#list attributes as attribute>
    <p> ${attribute.name}:</p> <input type = "text" name = "${attribute.name}"/> <br>
        <td>${attribute.value}</td>
    </tr>
</#list>
</table>

    <table border=1>
<#list grid as grid_row>
    <tr>
        <td>${grid_row.row_id}</td><td>${grid_row.invent}</td><td>${grid_row.count}</td>
        <td>${grid_row.price}</td>
        <td>${grid_row.sun}</td>
    </tr>
</#list>
    <input type="submit" name="save" value="save"/>
    <input type="submit" name="cancel" value="cancel"/>
<br>
</form>
</table>
</body>
</HTML>
