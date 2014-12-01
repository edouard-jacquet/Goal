<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="var.jsp"/>

<!DOCTYPE html>
<html lang='fr'>
	<head>
		<meta charset='utf-8' />
		<meta name='viewport' content='width=device-width, initial-scale=1' />
		<title>create account</title>
		<link rel='stylesheet' type='text/css' href='css/bootstrap.css' />
		<link rel='stylesheet' type='text/css' href='css/style.css' />
		<link rel='stylesheet' type='text/css' href='css/create-account.css' />
		<script src='js/jquery.js'></script>
		<script src='js/bootstrap.js'></script>
	</head>
	<body>
		<header id='header'>
			<div class='navigation-bar'>
				<div class='navigation-bar__header'>
					<button class='button navigation-bar__toggle' type='button'><span class='bootypo boottypo--list'></span></button>
					<a class='navigation-bar__brand' href='home'>Goal</a>
				</div>
			</div>
		</header>
		<section id='body'>
			<c:import url="notifications.jsp"/>
			<div class='grid'>
				<div class='row'>
					<div class='column small-12 medium-6 large-4 medium-centered'>
						<form class='panel' method='post' action='create-account'>
							<div class='panel__header grid'>
								<div class='row'>
									<div class='column small-12 text-center'>
										<h1>Create Account</h1>
									</div>
								</div>
							</div>
							<div class='panel__body grid'>
								<div class='row'>
									<div class='column small-12'>
										<input class='form__control' type='text' name='login' placeholder='Login'/>
									</div>
								</div>
								<div class='row'>
									<div class='column small-12'>
										<input class='form__control' type='password' name='password' placeholder='Password'/>
									</div>
								</div>
								<div class='row'>
									<div class='column small-12'>
										<input class='button button--primary button--block' type='submit' value='Create'/>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</section>
	</body>
</html>