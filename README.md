<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<br />

<!-- PROJECT LOGO -->
<div align="center">
  <img src="images/zookeeper.svg" alt="Logo" width="450" height="250">
  <h3 align="center">Apache Zookeeper</h3>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project
In this project, I explore the implementation of barriers and producer-consumer queues using Apache ZooKeeper, focusing on simplicity and clarity. The Barrier and Queue classes demonstrate fundamental distributed coordination patterns essential in distributed computing environments. These implementations assume the presence of a ZooKeeper server, which serves as the centralized coordination service for managing synchronization and messaging among distributed processes. By leveraging ZooKeeper's reliable and scalable features, these examples illustrate how to handle synchronization points (barriers) where processes synchronize before proceeding collectively, and how to manage message exchange (producer-consumer queues) efficiently. Through these demonstrations, developers can gain practical insights into utilizing ZooKeeper to enhance the coordination and communication capabilities of their distributed systems, ensuring robustness and consistency across multiple nodes in a networked environment.


### Built With
* ![JAVA](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
* ![Zookeeper](https://img.shields.io/badge/Zookeeper-355E3B)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

In order to run this project. You will need to have a JRE environment and a Zookeeper server up and running. 


### Prerequisites
<strong>Java 22</strong> and <strong>Zookeeper 3.8.4</strong>

You can download java from <a href="https://www.oracle.com/java/technologies/downloads/#java22">here</a>. You can find Zookeeper 3.8.4 in this <a href="https://neo4j.com/docs/operations-manual/current/installation/">link</a>. A step by step guide for setting up Zookeeper can be found <a href="https://zookeeper.apache.org/doc/current/zookeeperStarted.html">here</a>. 
### Installation

1 - Clone the code into you machine using .

```shell
git clone https://github.com/nabil-nablotech/zookeeper.git
```
<br/>

2 - Edit the zookeeper connection string environment variables by modifing <Strong>zookeeper.server.uri</Strong> property in the application.properties file in src/main/resources folder. If you are running, zookeeper keep the existing config as it is i.e <Strong>localhost</Strong>:

```shell
zookeeper.server.uri=localhost
```

<br/>
3 - Run a process. There are three types of process that you can run: producer, consumer and barriers. The producer randomly produces integers, the consumer dequeues and reads the first available data in the zookeeper queue and barrier executes an exit logic() after the specified number of process connect to zookeeper node. Use the following pattern to run the code. You will need to specify the number of data that will be produced or consumed or barrier threshold for the producer, consumer and barrier respectively. Detailed usage is available in the <a href="#usage">next section</a>.

```shell
.\mvnw spring-boot:run -Dspring-boot.run.arguments="[producer|consumer|barrier] [number]"
```
<br/>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->
## Usage

1 - Open 2 command line prompts to consume 100 data if available other wise listen for new incoming data. Run the following in each command prompt:
```shell
.\mvnw spring-boot:run -Dspring-boot.run.arguments="consumer 100"
```

<br/>

2 - Open 2 command line prompts to produce 100 data by each prompt. Run the following in each command prompt:
```shell
.\mvnw spring-boot:run -Dspring-boot.run.arguments="producer 100"
```

<br/>

3 - Check and observe the command line of the consumers running earlier.

<br/>

4 - Open 3 command line prompts so that one of them execute a logic (exit logic in this case) when the number of process connected reaches the speciifed number. Run the following in each command prompt:
```shell
.\mvnw spring-boot:run -Dspring-boot.run.arguments="barrier 2"
```

<br/>

5 - Check and observe the command line of the barriers running earlier. And check which one exits.

<br/>

<p align="right">(<a href="#readme-top">back to top</a>)</p>
