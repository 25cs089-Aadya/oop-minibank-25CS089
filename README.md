# MiniBank – Console Banking Application

CEUE203 Object-Oriented Programming (Java) mini project – 25CS089 Aadya

MiniBank is a menu-driven console banking app. You can open savings, current and
fixed deposit accounts, deposit, withdraw, transfer, view statements, run a batch
of transactions on a thread pool and generate an end-of-day report. Accounts are
saved to `data/accounts.dat` on exit and loaded again on start-up.

## Project structure

```
src/
  model/              Account, SavingsAccount, CurrentAccount, FixedDepositAccount,
                      Customer, Command, TransactionType, interfaces
  model/annotation/   @Id, @Positive, @MaxLength
  exception/          BankException and its subclasses
  util/               Validator, CommandParser, StatementFormatter, AnnotationValidator,
                      StatePersister, TransactionLog, ReportGenerator, Repository, AuditSession
  service/            MiniBank (main menu), BankService, TransactionProcessor,
                      TransactionBuffer, AccountStore, AccountWorker, LabDemos
test/service/         BankServiceTest (JUnit 5)
```

## Build and run (from the repo root, Windows PowerShell)

```powershell
javac -d out src\model\*.java src\model\annotation\*.java src\exception\*.java src\util\*.java src\service\*.java
Set-Content -Path manifest.txt -Value "Main-Class: service.MiniBank" -Encoding ascii
jar cfm minibank.jar manifest.txt -C out .
java -jar minibank.jar
```

## Practical demos

`LabDemos` shows the feature added in each practical:

```powershell
java -cp out service.LabDemos 8     # one practical (2 to 13)
java -cp out service.LabDemos       # all of them
```

## Tests

Download `junit-platform-console-standalone-1.10.2.jar` into a `lib` folder, then:

```powershell
javac -d out-test -cp "out;lib\junit-platform-console-standalone-1.10.2.jar" test\service\BankServiceTest.java
java -jar lib\junit-platform-console-standalone-1.10.2.jar -cp "out;out-test" --select-class service.BankServiceTest
```
