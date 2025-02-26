CREATE TABLE payment (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    down_payment NUMERIC(10,2),
    installments int,
    product_id UUID,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);