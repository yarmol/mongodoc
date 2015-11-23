<HTML>
<body>
<h1>Document ${doc_number}</h1>
<table border=1>

<#list grid as grid_row>
    <tr><td>${grid_row.row_id}</td><td>${grid_row.invent}</td><td>${grid_row.count}</td>

        <td>${grid_row.price}</td>
        <td>${grid_row.sun}</td>


    </tr>
</#list>
</table>
</body>
</HTML>
