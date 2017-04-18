<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'blogEntry.label', default: 'BlogEntry')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-blogEntry" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

        <div id="show-blogEntry" class="content scaffold-show" role="main">
            <h1>${this.blogEntry.title}</h1>
            <h2>By <span class="post-author">${this.blogEntry.poster.person.firstName} ${this.blogEntry.poster.person.lastName}</span></h2>



            <p class="blog-entry-text">${this.blogEntry.text}</p>

            <div class="blog-comments-div">
                <h2 class="header">Comments</h2>
                <div id="approved-comments-div">
                    <g:render template="/comment/blogentrycomment" model="[comments:unrejected_comments]"/>
                </div>
            </div>

            <sec:ifLoggedIn>
            <g:form controller="comment" action="save" params="[blogEntry: this.blogEntry.id, poster: user.id]">
                <div class="form-group">
                    <label for="commentarea">Add a new comment?</label>
                    <textarea class="form-control" id="commentarea" rows="3" name="text"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </g:form>
            </sec:ifLoggedIn>

            <g:if test="${user != null}">
            <div class="blog-comments-div">
                <g:render template="/comment/rejectedcomments" model="[comments:rejected_comments,user:user]"/>
            </div>
            </g:if>
        </div>
    </body>
</html>
