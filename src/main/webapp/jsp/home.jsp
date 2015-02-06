<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="var.jsp"/>

<!DOCTYPE html>
<html lang='fr'>
	<head>
		<meta charset='utf-8' />
		<meta name='viewport' content='width=device-width, initial-scale=1' />
		<title>home</title>
		<link rel='stylesheet' type='text/css' href='css/bootstrap.css' />
		<link rel='stylesheet' type='text/css' href='css/style.css' />
		<link rel='stylesheet' type='text/css' href='css/home.css' />
		<script src='js/jquery.js'></script>
		<script src='js/bootstrap.js'></script>
	</head>
	<body>
		<header id='header'>
			<div class='navigation-bar'>
				<div class='navigation-bar__header'>
					<button class='button navigation-bar__toggle' type='button'><span class='bootypo boottypo--list'></span></button>
				</div>
				<div class='navigation-bar__body'>
					<c:import url="guest.jsp"/>
				</div>
			</div>
		</header>
		<section id='body'>
			<c:import url="notifications.jsp"/>
			<div class='grid'>
				<div class='row'>
					<div class='column small-12 text-center'>
						<div class='logo'>
							<h3 class='logo__title'>
								<span class='logo__letter--blue'>G</span>
								<span class='logo__letter--red'>o</span>
								<span class='logo__letter--yellow'>a</span>
								<span class='logo__letter--green'>l</span>
							</h3>
						</div>
					</div>
				</div>
				<div class='row'>
					<div class='column small-12 medium-6 medium-centered'>
						<form method='get' action='search'>
							<div class='input-group'>
								<input class='form__control' type='text' name='query' placeholder='Search...'/>
								<span class='input-group__button'>
									<input class='button button--default' type='submit' value='Goal'/>
								</span>
							</div>
						</form>
					</div>
				</div>
			</div>
		</section>
	</body>
</html>