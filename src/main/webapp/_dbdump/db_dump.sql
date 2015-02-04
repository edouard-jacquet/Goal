--
-- Base de données :  `goal`
--

-- --------------------------------------------------------

--
-- Structure de la table `GOAL_DATAWAREHOUSE`
--

CREATE TABLE `GOAL_DATAWAREHOUSE` (
`DATAWAREHOUSE_ID` bigint(20) NOT NULL,
  `DATAWAREHOUSE_LOCATION` varchar(200) NOT NULL,
  `DATAWAREHOUSE_SUMMARIZE` text NOT NULL,
  `DATAWAREHOUSE_TITLE` varchar(200) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `GOAL_DATAWAREHOUSE`
--

INSERT INTO `GOAL_DATAWAREHOUSE` (`DATAWAREHOUSE_ID`, `DATAWAREHOUSE_LOCATION`, `DATAWAREHOUSE_SUMMARIZE`, `DATAWAREHOUSE_TITLE`) VALUES
(1, 'resource?type=dwh&&name=Best players score&url=storage/resource/datawarehouse/best_players.pdf', 'Best players goals score in three word cup 1998,2002,2006ordored by contry the best players are Ronaldo from Brazil,Miroslav klose from germany, thierry Henry from france.', 'Best players score'),
(2, 'resource?type=dwh&&name=World cup champions&url=storage/resource/datawarehouse/champions.pdf', 'The very first champion of FIFA world cup, organized in 1930 was Uruguay. The country hosted games at the same time that year. It has been champion once again in 1950. So far, others 7 countries have been champions of FIFA world cup once or may times. the world record of several wins is hold by Brazil which has been champion five times untill now.this makes it a great nation of football. Below is a list of all champions between 1930 and 2010. Definitely,only few Countries( 7 countries) have only had the chance of being World champions.', 'World cup champions'),
(3, 'resource?type=dwh&&name=France team players best score in word cup&url=storage/resource/datawarehouse/france_team.pdf', 'The France national football team represents France in FIFA Football. The national team has experienced much of its success during three major "golden generations": in the 1950s, 1980s, and 1990s respectively, which resulted in numerous major honours. France was one of the four European teams that participated in the inaugural World Cup in 1930 and, although having been eliminated in the qualification stage six times, is one of only three teams that have entered every World Cup cycle.In 1958, the team, led by Raymond Kopa and Just Fontaine, finished in third place at the FIFA World Cup. In 1984, France, led by Ballon d''Or winner Michel Platini, won UEFA Euro 1984. Under the leadership of Didier Deschamps and three-time FIFA World Player of the Year Zinedine Zidane, France became one of eight national teams to win the FIFA World Cup in 1998 when it hosted the tournament.', 'France team players best score in word cup'),
(4, 'resource?type=dwh&&name=Goal results analyze by Countries&url=storage/resource/datawarehouse/goal_results.pdf', 'This report represent results by the nation thaving the best score Brazil Uraguay, Argentina,France ,Italy,Germagny. The World Cup final matches are the last of the competition, and the results determine which country''s team is declared world champions. If after 90 minutes of regular play the score is a draw, an additional 30-minute period of play, called extra time, is added. If such a game is still tied after extra time it is decided by kicks from the penalty shoot-out. The winning penalty shoot-out area team are then declared champions.The tournament has been decided by a one-off match on every occasion except 1950, when the tournament winner was decided by a final round-robin group contested by four teams (Uruguay, Brazil, France, and Spain).', 'Goal results analyze by Countries'),
(5, 'resource?type=dwh&&name=Match and attendance for every word cup&url=storage/resource/datawarehouse/match_analyze.pdf', 'This report represents the number of match and attendance for every word cup by year and organizer as we can see they increase year after year.', 'Match and attendance for every word cup'),
(6, 'resource?type=dwh&&name=Organizers countries&url=storage/resource/datawarehouse/organizers_countries.pdf', 'Since first FIFA world cup organisation,in 1934,15 countries have participated in organizing and hosting games. Among them, 4 countries have arleady organized games twice.Those are France ,Germany, Italy and Mexico. Europe remains the continent wich hosted many games. It has arganized 10 games so far.South America is the 2nd with 4 word cup organization,North America 3 worlds cup so far.Asia and Africa has only hosted one game each one. The full list of countries that participated in differents world.', 'Organizers countries'),
(7, 'resource?type=dwh&&name=List of Football world cup winner&url=storage/resource/datawarehouse/winners.pdf', 'This report represents the list of Winners countries in all the world cup football since 1930 to 2010 .In the 19 tournaments held, 77 nations have appeared at least once. Of these, 12 have made it to the final match, and eight have won.With five titles, Brazil is the most successful World Cup team and also the only nation to have participated in every World Cup finals tournament.', 'List of Football world cup winner');

-- --------------------------------------------------------

--
-- Structure de la table `GOAL_USER`
--

CREATE TABLE `GOAL_USER` (
`USER_ID` bigint(20) NOT NULL,
  `USER_KEY` varchar(255) NOT NULL,
  `USER_LOGIN` varchar(100) NOT NULL,
  `USER_PASSWORD` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `GOAL_USER`
--

INSERT INTO `GOAL_USER` (`USER_ID`, `USER_KEY`, `USER_LOGIN`, `USER_PASSWORD`) VALUES
(1, '1tfnne4kapufvn91r69qidmbuh', 'admin', 'admin');

--
-- Index pour les tables exportées
--

--
-- Index pour la table `GOAL_DATAWAREHOUSE`
--
ALTER TABLE `GOAL_DATAWAREHOUSE`
 ADD PRIMARY KEY (`DATAWAREHOUSE_ID`);

--
-- Index pour la table `GOAL_USER`
--
ALTER TABLE `GOAL_USER`
 ADD PRIMARY KEY (`USER_ID`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `GOAL_DATAWAREHOUSE`
--
ALTER TABLE `GOAL_DATAWAREHOUSE`
MODIFY `DATAWAREHOUSE_ID` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT pour la table `GOAL_USER`
--
ALTER TABLE `GOAL_USER`
MODIFY `USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;