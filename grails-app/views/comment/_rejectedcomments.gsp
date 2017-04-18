<g:if test="${comments != [] && user != null}">
<h2 class="header">Comments rejected</h2>
<g:each in="${comments}" var="comment">
    <g:if test="${comment.poster.username == user.username || comment.blogEntry.poster.username == user.username}">
        <div class="alert alert-danger rejected-comment">
            <p>${comment.text}</p>
            <p class="comment-poster">by ${comment.poster.username}</p>
        </div>
    </g:if>
</g:each>
</g:if>
