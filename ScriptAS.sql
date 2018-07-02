USE mydb;

INSERT INTO mydb.utilizador VALUES (1, 'João', 'jnalm', 1234, 'joao@gmail.com', '1996-08-01', 'Portugal', 1000.0)
INSERT INTO mydb.ativo_has_portfolio(Ativo_idAtivo, Portfolio_idPortfolio) VALUES (1,1)
SELECT * FROM utilizador

SELECT at.idAtivo, at.Nome, at.ValorCompra, at.ValorVenda FROM ativo AS at
INNER JOIN ativo_has_portfolio AS ahp ON at.idAtivo = ahp.Ativo_idAtivo 
WHERE ahp.Portfolio_idPortfolio = 1;

SELECT uha.Ativo_idAtivo, at.ValorVenda, at.ValorCompra, uha.LimiteLucro, uha.LimitePerda, uha.Montante, uha.MontanteResultante FROM utilizador_has_ativo AS uha INNER JOIN ativo AS at ON uha.Ativo_idAtivo = at.idAtivo WHERE uha.Utilizador_idUtilizador = 1 AND 

UPDATE utilizador SET Plafond = 1000.0 WHERE idUtilizador = 1

SELECT * FROM utilizador

SELECT * FROM utilizador_has_ativo

SELECT IFNULL(SUM(Montante), 0) FROM utilizador_has_ativo WHERE Utilizador_idUtilizador = 1

DELETE FROM utilizador_has_ativo
WHERE Utilizador_idUtilizador > 0;

SELECT * FROM portfolio

SELECT * FROM ativo

SELECT idAtivo FROM ativo WHERE Tipo = 'TSLA'

SELECT * FROM ativo_has_portfolio

SELECT COUNT(idUtilizador) FROM utilizador;

DELETE FROM utilizador
WHERE idUtilizador = 3;

DELETE FROM portfolio
WHERE idPortfolio = 3;

DELETE FROM ativo
WHERE idAtivo > 0;

DELETE FROM ativo_has_portfolio
WHERE Ativo_idAtivo = 4;

INSERT INTO mydb.ativo VALUES (1, 'BTC', 'AÇÃO', 5681.69, 5682.07);
INSERT INTO mydb.ativo VALUES (2, 'AAPL', 'AÇÃO', 155.97, 155.88);
INSERT INTO mydb.ativo VALUES (3, 'AMZN', 'AÇÃO', 1001.62, 1000.91);
INSERT INTO mydb.ativo VALUES (4, 'GOOG', 'AÇÃO', 987.61, 987.13);
INSERT INTO mydb.ativo VALUES (5, 'GM', 'AÇÃO', 44.92, 44.81);
INSERT INTO mydb.ativo VALUES (6, 'OIL', 'COMMODITIES', 51.69, 51.64);
INSERT INTO mydb.ativo VALUES (7, 'GOLD', 'COMMODITIES', 1294.48, 1294.06);
INSERT INTO mydb.ativo VALUES (8, 'SILVER', 'COMMODITIES', 17.26, 17.21);
INSERT INTO mydb.ativo VALUES (9, 'NATGAS', 'COMMODITIES', 3.133, 3.133);
INSERT INTO mydb.ativo VALUES (10, 'PLATINUM', 'COMMODITIES', 937.3, 936.0);
INSERT INTO mydb.ativo VALUES (11, 'DJ30', 'INDICE', 22868.00, 22862.00);
INSERT INTO mydb.ativo VALUES (12, 'EUSTX30', 'INDICE', 3613.00, 3610.00);
INSERT INTO mydb.ativo VALUES (13, 'NSDQ', 'INDICE', 6076.70, 6073.70);
INSERT INTO mydb.ativo VALUES (14, 'JPN225', 'INDICE', 21211.00, 21201.00);
INSERT INTO mydb.ativo VALUES (15, 'ESP35', 'INDICE', 10278.00, 10271.00);
INSERT INTO mydb.ativo VALUES (16, 'ETHEREUM', 'MOEDA', 329.0268, 322.5222);
INSERT INTO mydb.ativo VALUES (17, 'XRP', 'MOEDA', 0.2432, 0.2371);
INSERT INTO mydb.ativo VALUES (18, 'LTC', 'MOEDA', 59.14, 57.39);
INSERT INTO mydb.ativo VALUES (19, 'DASH', 'MOEDA', 308.01, 298.91);

SELECT Username, Password FROM utilizador;