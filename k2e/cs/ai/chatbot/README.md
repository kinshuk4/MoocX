# Chatbot

A collection of awesome things regarding chat bots

#### Definition

Conversation-driven UI (CUI aka chatbots) now enables us to do everything from grabbing a taxi, to paying the electric bill or sending money to a friend. Offerings such as Siri, Google Now and Cortana demonstrate value to millions of people every day, particularly on mobile devices where the CUI can be superior to the GUI or complements it.*

The Conversation-driven UI respond to predefined queries/use-cases in Natural Language (Bots **don’t have to be smart**, [...] **just very-well designed**).

They allow for **enriched messages**: (Images, Audio, Video) and **clickable-elements** (Menus, Drop-down lists...).

**“Think of it as a mini version of a web page”**.

To build a Conversation-driven UI, two main tools are available:

1. Chatbot engines
2. Advanced Natural Language Processing Tools for Bot Makers
3. A UI

## Chat or chatbot based startups

- [msg.ai - AI for Conversational Commerce](http://msg.ai/)
- [wit.ai - Natural Language for Developers](https://wit.ai/)
- [api.ai - Conversational UX platform](https://api.ai/)
- [Chatfuel - Chatbot platform](http://chatfuel.com/)
- [Prompt - Chatbot platform](http://promptapp.io/)
- [Gupshup - Chatbot platform](https://www.gupshup.io/)
- [Magic - Text to get anything ondemand](https://getmagicnow.com/)
- [Good service - get anything done over chat](http://www.goodservice.in/)
- [Hyper - chat based Travel Agent](https://www.usehyper.com/)
- [Cleverbot](http://www.cleverbot.com/)
- [Sensay - The most helpful person you've never met](http://www.sensay.it/)
- [Assist - Bring services to messaging apps](https://www.assi.st/)
- [Notify - Conversational experiences that drive revenue] ([http://www.notify.io](http://www.notify.io/))
- [Backchannel - developer platform for building messaging bots] ([http://dev.backchannel.net/](http://dev.backchannel.net/))
- [Yourbot](http://www.yourbot.com/)

- [chattypeople](http://www.chattypeople.com/)
- [Botsify](http://botsify.com/)
- Smooch
- Facebook Messenger Platform
- Beep Boop



## Some Chatbot engines

### Big chatbot platforms

- [Wechat official account](https://admin.wechat.com/)
- [Slack](https://api.slack.com/bot-users)
- [Facebook messenger](https://developers.facebook.com/docs/messenger-platform)
- [Telegram](https://core.telegram.org/)
- [Microsoft Bot Framework](https://dev.botframework.com/)

### More

Provides API to quickly build a chatbot. Based on template based rules and/or using a trained model for entity and intent detection.

- [https://dev.botframework.com/](https://dev.botframework.com/) (microsoft, opensource sdk, connect to skype, text/sms, facebook messenger...)
- [https://messengerplatform.fb.com/](https://messengerplatform.fb.com/)
- [http://www.pandorabots.com/](http://www.pandorabots.com/) (chatbot engine (AIML), spellchecking)
- [https://core.telegram.org/bots](https://core.telegram.org/bots)
- Bot Interface for building Bots in Telegram ([https://github.com/yukuku/telebot](https://github.com/yukuku/telebot))
- [https://github.com/agermanidis/SnapchatBot](https://github.com/agermanidis/SnapchatBot)
- [https://github.com/hangoutsbot/hangoutsbot](https://github.com/hangoutsbot/hangoutsbot)

### Functionalities

- Allows for integration of "clickable elements".
  - menus, polls, forms, quick answers (small buttons, e.g. pick a size: 'small', 'medium', 'large')
  - location trackers, itinerary templates
  - cards,
  - photos, carousel,
  - videos,
  - audio,
  - files,
  - stickers, emoticons
- Easy connection to **a bunch of** communication platforms:Facebook MessengerSkypeSnapchatKIKemailsSMS

## Existing chatbots:

- [A. L. I. C. E.](http://alice.pandorabots.com/)
- [Mitsuku](http://www.mitsuku.com/)
- [Microsoft XiaoIce in Chinese](http://www.msxiaoice.com/DesktopLanding)
- [Microsoft Tay](https://twitter.com/tayandyou)

## Chatbot tools

- [TuringRobot－The most intelligent robot brain](http://www.tuling123.com/)
- [Facebook Messenger DevKit](https://github.com/olegakbarov/facebook-messenger-devkit)
- [http://viv.ai](http://viv.ai/) self generating conversational UI

## 

## Advanced Natural Language Processing Tools for Bot Makers

Provide functionalities to semi automatically build a model, train it, publish it so that it can be used by chatbot engine to trigger actions.

- Facebook Wit.ai Bot Engine
- Microsoft Language Understanding Intelligent Service (LUIS)
- Api.ai – conversational UX Platform (*German*, more built-in domains)
- [http://www.pandorabots.com/](http://www.pandorabots.com/) (learning feature in AIML, the bot directly learn from live conversation)

### Functionalities

- Using user-provided Entities, Intent or Pre-built entities and intent (e.g. coming from bing for LUIS) divided into **domains** (schedule flight, book taxi, bus timetables, emails, calls...)
- Can define actions (call a contact, book a meeting...)
- Interface to label data (pre-labeled data + importance of data to label), explore data...
- Improve model with **active learning:** "In the active learning process, LUIS identifies the interactions that it is relatively unsure of, and asks you to label them according to intent and entities. This has tremendous advantages: **LUIS knows what it is unsure of, and asks you to help where you will provide the maximum improvement in system performance**. Secondly, by focusing on the important cases, LUIS learns as quickly as possible, and takes the minimum amount of your time."
- REST API endpoint generation
- Import Unlabeled Utterances (text file with search queries)
- Import / Export JSON (**useful for semi automatically labeling data**)

### Pro / cons

- Interesting features:
  - Active learning
  - Semi-automatically label data (export!)
  - User Interface
- Limitations:
  - Languages (German not always there)
  - User-provided entities (max 10 per entity class for LUIS)

## Conclusion

Chatbots are a new kind of interface specifically suited for mobile devices:

- context-aware and personalized
- mobile-oriented UI.

They need **predefined and bounded use-cases** (look for shop/product in neighborhood, ask for availability/price, book an appointment, add to calendar, create itinerary, contact/message seller).

More or less user-friendly with help of built-in templates and NLP/machine learning.

Technical side: bot engines and learning platform seems mature enough at least for microsoft, facebook, api.ai and telegram.

Last but not least: How to start a conversation:

- 'Message us' button on web page
- Message code (like QR codes but for bots)
- Bot-store (like appstore, google play)


## Note:

- Resources gathered during a hackathon
- Feel free to pull request to add more resources

## One graph to rule it all [ref](http://www.economist.com/news/business-and-finance/21696477-market-apps-maturing-now-one-text-based-services-or-chatbots-looks-poised):

![7a0c8958-00a0-11e6-9d79-c871c7d3bd4f](./7a0c8958-00a0-11e6-9d79-c871c7d3bd4f.png)



https://github.com/shaohua/awesome-chatbot

https://www.quora.com/How-do-you-build-your-own-chat-bot-on-Slack

https://www.quora.com/What-is-the-best-way-to-teach-your-chat-bot-AI

https://www.quora.com/How-can-I-build-an-AI

https://www.quora.com/How-would-one-go-about-building-their-own-chat-bot-engine

https://github.com/xwzhong/papernote

https://github.com/aravindjcdeveloper/whatsapp-reverse-chatbot

Some good repos:

https://github.com/mckinziebrandon/DeepChatModels/tree/master/notebooks

https://github.com/hangoutsbot/hangoutsbot

https://github.com/hackerkid/bots

https://github.com/BotCube/awesome-bots

https://github.com/Conchylicultor/DeepQA

https://github.com/claudiajs/claudia-bot-builder

https://github.com/CognitiveBuild/Chatbot

https://github.com/gunthercox/ChatterBot

https://github.com/errbotio/errbot

https://github.com/litaio/lita

https://github.com/warmheartli/ChatBotCourse

https://github.com/jinfagang/weibo_terminater

https://github.com/chiphuyen/tf-stanford-tutorials

https://github.com/botpress/botpress

https://github.com/awslabs/aws-serverless-chatbot-sample