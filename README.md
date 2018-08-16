# authorisation coding assignment
An application that reads the authorisation properties file and loads the AuthzCache implementation. Then given a user principal, the actions they want to perform on a set of resources, the application should print PERMITTED or DENIED.

Example
A typical authorisation permissions file is as follows:
```
roles=RO;RW;SU
RO-Perm=Read:File
RW-Perm=Read,Write,Delete:File
Folder-Perm=Create,Delete:Folder
perms=RO->RO-Perm;RW->RW-Perm;SU->RW-Perm,Folder-Perm
users=dopey->RO;sneezy->RW;happy->RW;grumpy->SU

```

So: 
```
authzCache.isPermitted("happy", actions, resources);
with 
actions = "Write", "Delete", "Read"
  and
resources = "File"
would be PERMITTED;

while
authzCache.isPermitted("sneezy", actions, resources);
with 
actions = "Create", "Delete"
  and
resources = "Folder"
would be DENIED ;  
``` 

## Solution

The soloution was written with just Java 8 and Junit, no other framework

### Build

Maven:

```sh
mvn clean package 
```

### Usage
```
Run the main method in the com.jogsoft.apps.assignment.auth.AuthzApp class 
```
