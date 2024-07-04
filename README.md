# Cash-Register-Simulation

## Table of Contents
- [Project Status](#project-status)
- [Overview](#overview)
- [User Documentation](#user-documentation)
- [Developer Documentation](#developer-documentation)
- [Results](#results)

## Project Status 
This project is complete, but results section needs to be updated.

## Overview 
This program simulates a cash register with the capability to process USD in a foreign country that uses Euros. The program receives the amount of USD to purchase an item and then calculates the correct amount of change to return in Euros.

## User Documentation
The user must enter the amount of each USD currency paid to the cash register. If the user does not pay enough money, they must again enter the amount of each additional USD currency paid to the cash register. Upon paying enough money, the amount of each Euro currency in change will be dispensed. 

## Developer Documentation 
This main part of the simulation takes place in the constructor of ChangeComputation. The user will be prompted to enter the price of the desired item in Euros and the amount of each USD currency they will be paying with. If the user does not pay enough money the first time, the simulation will keep looping until they do. Otherwise, user error is not accounted for. The program ends by calculating the change in Euros and displaying each Euro currency to be returned to the user. The executable file is ChangeComputationTest. Supplemental JUnit tests are provided in ChangeComputationJUnit. 
![image](https://github.com/grlefl/Cash-Register-Simulation/assets/124198528/ffd66de5-65e3-4db0-9ef2-d0d420b672a2)

## Results
