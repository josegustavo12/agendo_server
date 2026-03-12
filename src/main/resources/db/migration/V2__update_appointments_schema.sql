-- Remove colunas da estrutura antiga de appointments (serviço único + valor em centavos)
ALTER TABLE appointments DROP COLUMN IF EXISTS service_type_id;
ALTER TABLE appointments DROP COLUMN IF EXISTS value_in_cents;

-- Adiciona total_amount (soma dos preços dos serviços)
ALTER TABLE appointments ADD COLUMN IF NOT EXISTS total_amount NUMERIC(10, 2);

-- Preenche valor padrão para registros existentes
UPDATE appointments SET total_amount = 0 WHERE total_amount IS NULL;

-- Aplica constraint NOT NULL após preencher os dados
ALTER TABLE appointments ALTER COLUMN total_amount SET NOT NULL;
