# My Revolut Passport by Ayrton Saito
This sample code will be my revolut passport to work in a amazing fintech in London, UK!

## How to run
For the usability purposes you can find the built revolut-passport-1.0.jar in the /run-here directory

```bash
java -jar ./run-here/revolut-passport-1.0.jar
```

## Info

Static code analysis: https://sonarcloud.io/dashboard?id=tonsaito_revolut-passport


## Future improvements
With only a few days to work on this project, I could not make everthing I wanted to make this project better, but here are some improvements that I would make:

```
- Install the coverage plugin for sonar
- PreparedStatment/Reflection on the DAO classes
- External configuration (12Factor)
- ETag validation (concurrency)
- More tests, as always
- JMeter Test (To validate concurrency and stress test)
```

## Usage

#### GET http://localhost:8080/client/all

**Sample Response**

```
[
    {
        "account": "001",
        "balance": 1000000,
        "id": 1,
        "name": "Tony Stark"
    },
    {
        "account": "002",
        "balance": 2500000,
        "id": 2,
        "name": "Bruce Wayne"
    },
    {
        "account": "003",
        "balance": 300,
        "id": 3,
        "name": "Peter Parker"
    },
    {
        "account": "004",
        "balance": 50,
        "id": 4,
        "name": "Thanos"
    },
    {
        "account": "005",
        "balance": 80000,
        "id": 5,
        "name": "Dr Strange"
    }
]
```
___

#### POST http://localhost:8080/exchange/money

**Body**
```
{
	"accountFrom": "001",
	"accountTo": "002",
	"amount": 333
}
```

**Possible Responses**

```
[400] Invalid client FROM Account
[400] Invalid client TO Account
[400] Account From and account TO MUST be different
[400] Invalid funds :(
[400] Invalid amount of money
[200] Money Exchange successfully completed!
```

___

#### GET http://localhost:8080/exchange/history
```
[
    {
        "accountFrom": "001",
        "accountTo": "003",
        "amount": 10,
        "date": 1558915502477,
        "id": 1,
        "info": "",
        "status": true
    },
    {
        "accountFrom": "001",
        "accountTo": "003",
        "amount": 10,
        "date": 1558915510461,
        "id": 2,
        "info": "",
        "status": true
    }
]
```

## License
[GNU](https://www.gnu.org/licenses/gpl-3.0.pt-br.html)