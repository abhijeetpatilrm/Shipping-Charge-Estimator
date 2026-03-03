-- Sample data for Shipping Charge Estimator
-- This file is automatically executed by Spring Boot on application startup

-- =====================================================
-- CUSTOMERS (Kirana Stores) - Different locations across India
-- =====================================================
INSERT INTO customers (id, name, phone_number, latitude, longitude) VALUES (1, 'Mumbai Kirana Store', '9876543210', 19.0760, 72.8777);
INSERT INTO customers (id, name, phone_number, latitude, longitude) VALUES (2, 'Delhi General Store', '9876543211', 28.6139, 77.2090);
INSERT INTO customers (id, name, phone_number, latitude, longitude) VALUES (3, 'Bangalore Provisions', '9876543212', 12.9716, 77.5946);
INSERT INTO customers (id, name, phone_number, latitude, longitude) VALUES (4, 'Chennai Super Store', '9876543213', 13.0827, 80.2707);
INSERT INTO customers (id, name, phone_number, latitude, longitude) VALUES (5, 'Hyderabad Traders', '9876543214', 17.3850, 78.4867);

-- =====================================================
-- SELLERS - Located in different regions
-- =====================================================
INSERT INTO sellers (id, name, latitude, longitude) VALUES (1, 'Karnataka Food Suppliers', 12.9716, 77.5946);
INSERT INTO sellers (id, name, latitude, longitude) VALUES (2, 'Maharashtra Distributors', 19.0760, 72.8777);
INSERT INTO sellers (id, name, latitude, longitude) VALUES (3, 'Tamil Nadu Wholesale', 13.0827, 80.2707);

-- =====================================================
-- WAREHOUSES - Strategic locations for distribution
-- =====================================================
INSERT INTO warehouses (id, name, latitude, longitude) VALUES (1, 'Bangalore Central Warehouse', 12.9716, 77.5946);
INSERT INTO warehouses (id, name, latitude, longitude) VALUES (2, 'Mumbai Logistics Hub', 19.0760, 72.8777);
INSERT INTO warehouses (id, name, latitude, longitude) VALUES (3, 'Delhi Distribution Center', 28.6139, 77.2090);
INSERT INTO warehouses (id, name, latitude, longitude) VALUES (4, 'Chennai Storage Facility', 13.0827, 80.2707);
INSERT INTO warehouses (id, name, latitude, longitude) VALUES (5, 'Hyderabad Warehouse', 17.3850, 78.4867);

-- =====================================================
-- PRODUCTS - Various products with different weights
-- =====================================================
INSERT INTO products (id, name, price, weight, length, width, height, seller_id) VALUES (1, 'Rice Bag (25kg)', 1500.00, 25.0, 50.0, 30.0, 20.0, 1);
INSERT INTO products (id, name, price, weight, length, width, height, seller_id) VALUES (2, 'Wheat Flour (10kg)', 600.00, 10.0, 40.0, 25.0, 15.0, 1);
INSERT INTO products (id, name, price, weight, length, width, height, seller_id) VALUES (3, 'Sugar Pack (5kg)', 300.00, 5.0, 30.0, 20.0, 10.0, 1);
INSERT INTO products (id, name, price, weight, length, width, height, seller_id) VALUES (4, 'Cooking Oil (15L)', 1800.00, 13.5, 35.0, 25.0, 30.0, 1);
INSERT INTO products (id, name, price, weight, length, width, height, seller_id) VALUES (5, 'Pulses Mix (20kg)', 2000.00, 20.0, 45.0, 30.0, 20.0, 2);
INSERT INTO products (id, name, price, weight, length, width, height, seller_id) VALUES (6, 'Tea Powder (2kg)', 800.00, 2.0, 20.0, 15.0, 10.0, 2);
INSERT INTO products (id, name, price, weight, length, width, height, seller_id) VALUES (7, 'Spices Set (3kg)', 1200.00, 3.0, 25.0, 20.0, 15.0, 2);
INSERT INTO products (id, name, price, weight, length, width, height, seller_id) VALUES (8, 'Salt Bag (10kg)', 200.00, 10.0, 40.0, 25.0, 15.0, 2);
INSERT INTO products (id, name, price, weight, length, width, height, seller_id) VALUES (9, 'Coffee Beans (5kg)', 2500.00, 5.0, 30.0, 20.0, 15.0, 3);
INSERT INTO products (id, name, price, weight, length, width, height, seller_id) VALUES (10, 'Jaggery Block (8kg)', 600.00, 8.0, 35.0, 25.0, 10.0, 3);
INSERT INTO products (id, name, price, weight, length, width, height, seller_id) VALUES (11, 'Coconut Oil (10L)', 1500.00, 9.2, 30.0, 30.0, 25.0, 3);
INSERT INTO products (id, name, price, weight, length, width, height, seller_id) VALUES (12, 'Instant Noodles Box (12kg)', 900.00, 12.0, 40.0, 35.0, 25.0, 3);
