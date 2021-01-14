
##Registration Service


Registration Service Docker built command
```shell script
docker build -t registrationservice:1.0 .
```

Registration Service Docker run command
```shell script
docker run -d -p 8086:8086 --rm registrationservice:1.0
```

***
##### Registration service endpoints
> [localhost:8086](http://localhost:8086)
>
> [localhost:8086/slow](http://localhost:8086/slow)
>
> [localhost:8086/error](http://localhost:8086/error)
>
> [localhost:8086/erratic](http://localhost:8086/erratic)
>
---
>
> [localhost:8086/registration](http://localhost:8086/registration)
>
> [localhost:8086/registration/addSeller](http://localhost:8086/registration/addSeller)
>
***

> want to test we can use apache bench


- [x] docker registration service