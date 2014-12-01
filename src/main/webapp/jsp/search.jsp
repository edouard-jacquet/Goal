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
	</head>
	<body>
		<header id='header'>
			<div class='navigation-bar navigation-bar--default'>
				<div class='navigation-bar__header'>
					<button class='button navigation-bar__toggle' type='button'><span class='bootypo bootypo--list'></span></button>
					<a class='navigation-bar__brand' href='home'>Goal</a>
				</div>
				<div class='navigation-bar__body'>
					<form class='navigation-bar__form navigation-bar--left' method='post' action='search'>
						<div class='input-group'>
							<input class='form__control' type='text' name='search' placeholder='Search...' value='<c:out value="${query}"/>'/>
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
					<c:if test="${results != null && results.size() > 0}">
						<c:forEach var="result" items="${results}" varStatus="idx">
							<div class='column small-12'>
								<div class='thumbnail'>
									<div class='thumbnail__caption'>
										<h3><c:out value="${result.title}"/></h3>
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
					<li class='disabled'><a href='#'>&larr;</a></li>
					<li class='active'><a href='#'>1</a></li>
					<li class='disabled'><a href='#'>&rarr;</a></li>
				</ul>
			</c:if>
		</footer>
	</body>
</html>