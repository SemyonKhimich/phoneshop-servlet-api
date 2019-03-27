<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="field" required="true" %>
<%@ attribute name="query" required="true" %>
<a href="?query=${query}&order=asc&field=${field}">asc</a>
<a href="?query=${query}&order=desc&field=${field}">desc</a>
