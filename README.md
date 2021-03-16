# codingtest


When application run, results are logged both in console and in file "./log/log.log"

On console, only results for product "TEST-PRODUCT" are displayed, to facilitate verification.

On log file, all logs are saved.



Addition :
Classes: Product, Transaction and ProductManager.

Product, parameter:
  String productId;
  double fairValue;
  List<Transaction> latestTransactions;
  double vwap;

Transaction, parameter:
  long quantity;
  double price;
  

ProductManager stores all products, updates them when transaction or fairvaluechanged occur, and logs when conditions met.
