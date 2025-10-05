# Clone

**Clone** is a friendship-focused app that helps you find people who talk like you and share your vibe.  
You upload your conversations, choose the 3 people who you have the best conversations with and our app will find a clone of them for you.  

✨ **Features:**  
- Upload past conversation logs (chats, messages)  
- Backend analyzes your communication patterns and interests  
- Get friend recommendations based on similarity of conversation dynamics  
- Focus on genuine friendship, not dating  
- Data privacy: Your conversations stay secure
- allows you to message these people

🚀 **Tech Stack:**  
- Java Spring Boot (backend)  
- html,css,js (frontend)  
- postgresQL + (pg vector)
- google cloud console
- 
---

## 🌈 Why Clone?

Clone is not just about meeting people — it's about finding more people who you alredy get along with.   
No more random swipes or shallow matches — just real connections through whats worked already. 

how to use:
download a postgres+pg-vector image andrun on docker on port 5432. set username and password as waht you would like in application porperties.
create a google cloud console account, click on new proejct and APIs & Services → OAuth consent screen. set it up and paste client id and seret into the applicaitons.properties. 

endpoints: 
Mapped Endpoints:
{GET [/api/test/getData]} 
{GET [/logout]}
{GET [/match/accept-requests]}
{GET [/match/next]}
{GET [/login?expired=true]}
{ [/error]}
{GET [/api/conversation/conversation/{name}/getLaughs], produces [application/json;charset=UTF-8]}
{GET [/login]}
{POST [/match/{matchId}/reject]}
{POST [/api/test/connection]}
{GET [/home/about]}
{GET [/home]}
{GET [/match/home]}
{GET [/api/conversation/fileStats/{folderName}], produces [application/json;charset=UTF-8]}
{POST [/api/conversation/getMessagesByAttributes], produces [application/json;charset=UTF-8]}
{POST [/login]}
{GET [/api/user/profile]}
{POST [/api/noStorage/upload]}
{GET [/conversation/{conversationId}]}
{POST [/api/user/profile]}
{GET [/message/friendships]}
{GET [/match/requests]}
{DELETE [/message/friendships/{friendId}]}
{GET [/api/conversation/fileStatsTest], produces [application/json;charset=UTF-8]}
{GET [/api/user/home]}
{GET [/user/home]}
{POST [/match/swipe-right]}
{GET [/api/noStorage/ping]}
{GET [/message/home]}
{POST [/api/noStorage/pingpost]}
{GET [/home/contact]}
{POST [/match/swipe-left]}
{GET [/api/noStorage/{folderName}]}
{POST [/match/{matchId}/accept]}
{GET [/api/conversation/getSurroundingMessages], produces [application/json;charset=UTF-8]}
{GET [/login?invalid=true]}
{POST [/match/block]}
