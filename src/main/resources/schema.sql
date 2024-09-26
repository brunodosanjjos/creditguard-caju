USE db_creditguard;

-- Tabela para usuarios e saldos: tb_crgd_account
CREATE TABLE IF NOT EXISTS tb_crgd_account (
                                          account_id VARCHAR(255) NOT NULL PRIMARY KEY,
                                          amount_cash DOUBLE,
                                          amount_meal DOUBLE,
                                          amount_food DOUBLE
);

-- Tabela para transações: tb_crgd_transaction
CREATE TABLE IF NOT EXISTS tb_crgd_transaction (
                                              transaction_id VARCHAR(255) NOT NULL PRIMARY KEY,
                                              account_id VARCHAR(255) NOT NULL,
                                              amount DOUBLE NOT NULL,
                                              mcc VARCHAR(255) NOT NULL,
                                              merchant VARCHAR(255) NOT NULL,
                                              transaction_date DATETIME NOT NULL,
                                              result_transaction VARCHAR(50),
                                              FOREIGN KEY (account_id) REFERENCES tb_crgd_account(account_id)
);

-- Tabela para tipos de estabelecimentos: tb_mcc_type
CREATE TABLE IF NOT EXISTS tb_mcc_type (
                                                     type_id VARCHAR(255) NOT NULL PRIMARY KEY,
                                                     type_name VARCHAR(255)
);

-- Tabela de estabelecimentos e codigos: tb_mcc
CREATE TABLE IF NOT EXISTS tb_mcc (
                                      code VARCHAR(255) NOT NULL PRIMARY KEY,
                                      type_id VARCHAR(255) NOT NULL,
                                      description VARCHAR(255),
                                      FOREIGN KEY (type_id) REFERENCES tb_mcc_type(type_id)
);



-- Tabela para nome dos estabelecimentos: tb_merchant
CREATE TABLE IF NOT EXISTS tb_merchant (
                                               merchant_name VARCHAR(255) NOT NULL,
                                               mcc VARCHAR(4) NOT NULL,
                                               PRIMARY KEY (merchant_name),
                                               FOREIGN KEY (mcc) REFERENCES tb_mcc(code)
);
