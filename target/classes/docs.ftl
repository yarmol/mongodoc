<HTML>
<body>
<h1>Documents</h1>
<h3>user: ${user}</h3>
<h3>time: ${time}</h3>
<table border=1>
<#list table as row>
<tr><td><a href="/page/docid/${row._id}">${row.number}</a></td><td>${row.author}</td><td>${row.kind}</td><td>${row.doc_sum}</td></tr>
</#list>
</table>
</body>
</HTML>
