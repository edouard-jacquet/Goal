<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="var.jsp"/>

<!DOCTYPE html>
<html lang='fr'>
	<head>
		<meta charset='utf-8' />
		<meta name='viewport' content='width=device-width, initial-scale=1' />
		<title>search</title>
		<link rel='stylesheet' type='text/css' href='css/bootstrap.css' />
		<link rel='stylesheet' type='text/css' href='css/style.css' />
		<link rel='stylesheet' type='text/css' href='css/search.css' />
		<script src='js/jquery.js'></script>
		<script src='js/bootstrap.js'></script>
		<script src='js/search.js'></script>
	</head>
	<body>
		<header id='header'>
			<div class='navigation-bar navigation-bar--default'>
				<div class='navigation-bar__header'>
					<button class='button navigation-bar__toggle' type='button'><span class='bootypo bootypo--list'></span></button>
					<a class='navigation-bar__brand' href='home'>Goal</a>
				</div>
				<div class='navigation-bar__body'>
					<form class='navigation-bar__form navigation-bar--left' method='get' action='search'>
						<div class='input-group'>
							<input class='form__control' type='text' name='query' placeholder='Search...' value='<c:out value="${query}"/>'/>
							<span class='input-group__button'>
								<input class='button button--default' type='submit' value='Goal'/>
							</span>
						</div>
					</form>
					<c:import url="guest.jsp"/>
				</div>
			</div>
		</header>
		<section id='body'>
			<c:import url="notifications.jsp"/>
			<div class='grid'>
				<div class='row'>
					<div class='column small-12'>
						<c:if test="${suggestions != null && suggestions.size() > 0}">
							<ul class='navigation navigation--pills'>
								<c:forEach var="suggestion" items="${suggestions}" varStatus="idx">
									<li><a href='search?query=<c:out value="${suggestion}"/>'><c:out value="${suggestion}"/></a></li>
								</c:forEach>
							</ul>
						</c:if>
					</div>
				</div>
				<div class='row'>
					<c:if test="${results != null && results.size() > 0}">
						<c:forEach var="result" items="${results}" varStatus="idx">
							<c:if test='${idx.index >= 10*(pageCurrent-1) && idx.index < 10*pageCurrent}'>
								<div class='column small-12'>
									<div class='thumbnail'>
										<div class='thumbnail__caption'>
											<h3>
												<c:if test="${result.getClass().simpleName == 'FileResult'}"><span class='bootypo bootypo--txt'></span></c:if>
												<c:if test="${result.getClass().simpleName == 'WebResult'}"><span class='bootypo bootypo--web'></span></c:if>
												<c:if test="${result.getClass().simpleName == 'DWHResult'}"><span class='bootypo bootypo--dwh'></span></c:if>
												<c:out value="${result.title}"/>
											</h3>
											<p class='text-justify'>
												<c:out value="${result.summarize}"/>
											</p>
											<p>
												<a class='button button--default' href='<c:out value="${result.location}"/>'>Readme</a>
											</p>
											<p>
												Score : <c:out value="${result.score}"/>
											</p>
										</div>
									</div>
								</div>
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test='${results != null && results.size() == 0}'>
						<div class='column small-12'>
							<h3>No Results Found.</h3>
							<p>
								Your search : <span class='bold'><c:out value="${query}"/></span>, did not match any documents.
							</p>
						</div>
					</c:if>
				</div>
			</div>	
		</section>
		<footer id='footer' class='text-center'>
			<c:if test="${results != null && results.size() > 0}">
				<ul class='pagination'>
					<c:if test='${pageCurrent == 1}'>
						<li class='disabled'><a href='#'>&larr;</a></li>
					</c:if>
					<c:if test='${pageCurrent != 1}'>
						<li><a href='search?query=<c:out value="${query}"/>&page=<c:out value="${pageCurrent-1}"/>'>&larr;</a></li>
					</c:if>
					<c:forEach begin="1" end="${pageMax}" step="1" var="index" varStatus="idx">
						<c:if test='${index != pageCurrent}'>
							<li><a href='search?query=<c:out value="${query}"/>&page=<c:out value="${index}"/>'><c:out value="${index}"/></a></li>
						</c:if>
						<c:if test='${index == pageCurrent}'>
							<li class='active'><a href='#'><c:out value="${index}"/></a></li>
						</c:if>
					</c:forEach>
					<c:if test='${pageCurrent == pageMax}'>
						<li class='disabled'><a href='#'>&rarr;</a></li>
					</c:if>
					<c:if test='${pageCurrent != pageMax}'>
						<li><a href='search?query=<c:out value="${query}"/>&page=<c:out value="${pageCurrent+1}"/>'>&rarr;</a></li>
					</c:if>
				</ul>
			</c:if>
		</footer>
	</body>
</html>