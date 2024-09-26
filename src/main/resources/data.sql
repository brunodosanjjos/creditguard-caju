USE db_creditguard;

-- Inserindo dados na tabela tb_mcc_type
INSERT INTO tb_mcc_type (type_id, type_name)
SELECT 1, 'FOOD' WHERE NOT EXISTS (SELECT 1 FROM tb_mcc_type WHERE type_id = 1);

INSERT INTO tb_mcc_type (type_id, type_name)
SELECT 2, 'MEAL' WHERE NOT EXISTS (SELECT 1 FROM tb_mcc_type WHERE type_id = 2);

INSERT INTO tb_mcc_type (type_id, type_name)
SELECT 3, 'CASH' WHERE NOT EXISTS (SELECT 1 FROM tb_mcc_type WHERE type_id = 3);

-- Inserindo dados na tabela tb_mcc
INSERT INTO tb_mcc (code, type_id, description)
SELECT '5411', 1, 'MERCEARIAS/SUPERMERCADOS' WHERE NOT EXISTS (SELECT 1 FROM tb_mcc WHERE code = '5411');

INSERT INTO tb_mcc (code, type_id, description)
SELECT '5412', 1, 'ACOUGUEIRO' WHERE NOT EXISTS (SELECT 1 FROM tb_mcc WHERE code = '5412');

INSERT INTO tb_mcc (code, type_id, description)
SELECT '5811', 2, 'DISTRIBUICAO E PRODUCAO DE ALIMENTOS' WHERE NOT EXISTS (SELECT 1 FROM tb_mcc WHERE code = '5811');

INSERT INTO tb_mcc (code, type_id, description)
SELECT '5812', 2, 'RESTAURANTES' WHERE NOT EXISTS (SELECT 1 FROM tb_mcc WHERE code = '5812');

INSERT INTO tb_mcc (code, type_id, description)
SELECT '4121', 3, 'LIMUSINES E TÁXIS' WHERE NOT EXISTS (SELECT 1 FROM tb_mcc WHERE code = '4121');

INSERT INTO tb_mcc (code, type_id, description)
SELECT '4111', 3, 'TRANSPORTE LOCAL DE PASSAGEIROS' WHERE NOT EXISTS (SELECT 1 FROM tb_mcc WHERE code = '4111');

INSERT INTO tb_mcc (code, type_id, description)
SELECT '4829', 3, 'ORDENS DE PAGAMENTO POR TRANSFERENCIA BANCARIA' WHERE NOT EXISTS (SELECT 1 FROM tb_mcc WHERE code = '4829');

-- Inserindo dados na tabela tb_crgd_account
INSERT INTO tb_crgd_account (account_id, amount_cash, amount_meal, amount_food)
SELECT '1001', 1000.00, 400.00, 400.00 WHERE NOT EXISTS (SELECT 1 FROM tb_crgd_account WHERE account_id = '5678');

INSERT INTO tb_crgd_account (account_id, amount_cash, amount_meal, amount_food)
SELECT '1002', 500.00, 100.00, 100.00 WHERE NOT EXISTS (SELECT 1 FROM tb_crgd_account WHERE account_id = '1234');

-- Inserindo dados na tabela tb_merchant
INSERT INTO tb_merchant (merchant_name, mcc)
SELECT 'UBER TRIP SAO PAULO BR', '4121' WHERE NOT EXISTS (SELECT 1 FROM tb_merchant WHERE merchant_name = 'UBER TRIP SAO PAULO BR');

INSERT INTO tb_merchant (merchant_name, mcc)
SELECT 'UBER EATS SAO PAULO BR', '5812' WHERE NOT EXISTS (SELECT 1 FROM tb_merchant WHERE merchant_name = 'UBER EATS SAO PAULO BR');

INSERT INTO tb_merchant (merchant_name, mcc)
SELECT 'PAG*JoseDaSilva RIO DE JANEI BR', '4829' WHERE NOT EXISTS (SELECT 1 FROM tb_merchant WHERE merchant_name = 'PAG*JoseDaSilva RIO DE JANEI BR');

INSERT INTO tb_merchant (merchant_name, mcc)
SELECT 'PICPAY*BILHETEUNICO GOIANIA BR', '4111' WHERE NOT EXISTS (SELECT 1 FROM tb_merchant WHERE merchant_name = 'PICPAY*BILHETEUNICO GOIANIA BR');
