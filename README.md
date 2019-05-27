# My Revolut Passport by Ayrton Saito
This sample code will be my revolut passport to work in a amazing fintech in London, UK!

# How to run
For the usability purposes you can find the built revolut-passport.jar in the /build/libs directory

```bash
java -jar /build/libs/revolut-passport.jar
```

## Usage

### GET http://localhost:8080/client/all

**Response**

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

**Response**
```

```

___

#### GET http://localhost:8080/exchange/history

## Possible Improvements
With only a few days to work on this project, I could not make everthing I wanted to make this project better, but here some improvements that I would make:

Reflection on on the DAO classes

## License
[GNU](https://www.gnu.org/licenses/gpl-3.0.pt-br.html)