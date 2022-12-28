# Multi-Thread Order Handler

This Java program is designed to replicate order processing for Black Friday. The purpose is to process orders and the individual items within them concurrently. Input files with orders and products have to be read and output files with shipped orders and products are be produced. The program is able to detect when all products in an order have been dispatched and marks the entire order as shipped. The "Task.pdf" file can provide more comprehensive information for romanian readers.

# Input and Output Files Format

The program receives two types of text files:

1. orders.txt, with multiple lines formatted as: order_id, no_of_products;
2. order_products.txt, which contains the products found in an order, formatted as: order_id, product_id.

After the program is ran, it will mark the shipped orders and items as such, and store the data in two files:

1. orders_out.txt, formatted as: order_id, no_of_products, shipped;
2. order_products_out.txt, with the following format: order_id, product_id, shipped.

# Approach

The project was solved by using multi-level threading.

Level 1:
- Every thread processes a line at a time from "orders.txt" file in the "SplitOrders" class.
- After an line is processed, the program checks if the order is empty, and should be ignored, or if it has products that need to be shipped.
- If the order contains items, multiple threads are started by the thread pool implemented with the "ExecutorService" interface with the purpose of limiting the number of threads which can run at a time.

Level 2:
- Each thread reads lines from the "order_products.txt" file.
- If the processed item is part of the order assigned to the "worker" (thread), then it marks it as shipped and moves on.
- Each thread takes care of one item at a time, and moves on to where the others left off (each thread reads a few lines and processes several items).

When all the items within an order are marked as "shipped" and written in the "order_products_out.txt" file, the order itself is marked as "shipped" and written in the "orders_out.txt" file.

## Testing

In order to test the program, it may be compiled with the "Makefile" found in the "src" directory and ran as:
```
java Main <folder_input> <nr_max_threads>
```

Or, it may be tested with the checker assigned, found in the "checker" directory.
