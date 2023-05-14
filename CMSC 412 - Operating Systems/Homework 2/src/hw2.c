/* 
 * Author: Luis Moreno
 * Date: January 23, 2023
 * Class: CMSC 412 6380 Operating Systems (2232)
 * 
 * Homework 2:
 * This program runs five processes concurrently. Two child processes are
 * created from the parent process. Then, the two children create their own
 * child processes (grandchildren to the original parent).
 * 
 * Each time a process is created, it is checked for a fork() error (value = -1).
 * The Parent process waits for the Child processes to finish before printing its information.
 * The Child processes wait for their respective GrandChild processes 
 * to finish before printing their information.
 */
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main()
{
    int pid, parent, child1, child2;
    parent = getpid();          // save parent pid
    pid = fork();               // create child1
    if (pid == -1)
        printf("Fork error.\n");
    
    if (getpid() != parent)
        child1 = getpid();      // save child1 pid
    
    if (getpid() == parent)     // create child2
    {
        pid = fork();
        if (pid == -1)
            printf("Fork error.\n");
    }
    
    if (getpid() != parent && getpid() != child1)
        child2 = getpid();      // save child2 pid
    
    if (getpid() != parent)
    {
        pid = fork();           // create grandchildren
        if (pid == -1)
            printf("Fork error.\n");
    }
    
    if (getppid() == parent)    // print Child1 & Child2
    {
        while(wait(NULL) > 0);  // used to make sure parent waits for ALL children to finish
        if (getpid() == child1)
            printf("I am process Child1 and my pid is %d\n", getpid());
        if (getpid() == child2)
            printf("I am process Child2 and my pid is %d\n", getpid());
    }

    if (getppid() == child1)    // print GrandChild1
    {
        printf("I am process GrandChild1 and my pid is %d\n", getpid());
        printf("My parent is process Child1 and it has the following pid: %d\n", getppid());
    }
        
    if (getppid() == child2)    // print GrandChild2
    {
        printf("I am process GrandChild2 and my pid is %d\n", getpid());
        printf("My parent is process Child2 and it has the following pid: %d\n", getppid());
    }

    if (getpid() == parent)     // print Parent
    {
        while(wait(NULL) > 0);  // used to make sure parent waits for ALL children to finish
        printf("\nI am the Parent process and my pid is %d\n", getpid());
        printf("Both my children finished their execution.\n");
    }
    return 0;
}