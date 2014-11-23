<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang='fr'>
	<head>
		<meta charset='utf-8' />
		<title>connexion</title>
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="css/style.css" />
		<link rel="stylesheet" type="text/css" href="css/connexion.css" />
		<script src='js/jquery.js'></script>
		<script src='js/bootstrap.js'></script>
	</head>
	<body>
		<header id='header'>
			<div class='navigation-bar'>
				<div class='navigation-bar__header'>
					<button class='button navigation-bar__toggle' type='button'><span class='bootypo boottypo--list'></span></button>
				</div>
			</div>
		</header>
		<section id='body' class='text-center'>
			<div class='grid'>
				<div class='row'>
					<div class='column small-12 medium-6 medium-centered'>
						<form method='post' action='connexion'>
							<div class='input-group'>
								<input class='form__control' type='text' placeholder='Search...'/>
								<span class='input-group__button'>
									<input class='button button--default' type='submit' value='Connexion'/>
								</span>
							</div>
						</form>
					</div>
				</div>
			</div>
		</section>
	</body>
</html>