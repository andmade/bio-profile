<g:each in="${comments}" var="comment">
    <g:if test="${comment.approved == true}">
    <div class="well well-lg">
        <p>${comment.text}</p>
        <p class="comment-poster">by ${comment.poster.username}</p>
    </div>
    </g:if>
    <g:if test="${comment.approved == false && comment.rejected == false}">
    <div id="${'comment'+comment.id}" class="alert alert-warning" >
        <p>${comment.text}</p>
        <p class="comment-poster">by ${comment.poster.username}</p>
        <div class="comment-moderation-buttons">
            <button type="button" class="btn btn-success approve-button" data-comment-id="${comment.id}" data-blogentry-id="${this.blogEntry.id}">Approve</button>
            <button type="button" class="btn btn-danger reject-button" data-comment-id="${comment.id}" data-blogentry-id="${this.blogEntry.id}">Reject</button>
        </div>
    </div>
    </g:if>
</g:each>