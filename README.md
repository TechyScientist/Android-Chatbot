# Android Chatbot
An AI chatbot made for Android with Android Studio, Kotlin, and XML.

### Initial Setup
On first launch, the app will download the **Gemma 4 Uncensored MAX** model by **PeppX**. 
This action cannot be canceled, and will be restarted (after deleting previously downloaded data) 
if it is interrupted.

**Note**: The app will not be usable until the model is completely downloaded.

### Setting Model Parameters
Once the model is downloaded, users will be able to set a variety of parameters to change the behaviour
of their chatbot. Once set, parameters for the model cannot be changed without uninstalling and 
reinstalling the app.

| Parameter Group | Default Setting |
|-----------------|-----------------|
| Name            | --              |
| Role            | Friend          |
| Assertiveness   | Neutral         |
| Warmth          | Friendly        |
| Formality       | Standard        |
| Impulsiveness   | Balanced        |
| Playfulness     | Light-hearted   |
| Sensory Focus   | Balanced        |
| Maturity Rating | Safe (PG-13)    |

### Parameter Explanations
#### Name
Pretty self-explanatory, what you'd like to call your chatbot.

#### Role
What role is this chatbot playing for you?

| Parameter Value | Description                                                                                                    |
|-----------------|----------------------------------------------------------------------------------------------------------------|
| Friend          | A warm, relaxed, and casual dynamic built on shared comfort and easygoing trust.                               |
| Girlfriend      | An intimate, loving romantic relationship filled with affection and romantic warmth. Chatbot plays a *female*. |
| Boyfriend       | An intimate, loving romantic relationship filled with affection and romantic warmth. Chatbot plays a *male*.   |
| Companion       | A dedicated partner and confidant sharing a deep, unbreakable bond of absolute loyalty.                        |
| Partner         | An equal companion where you face everything together as a deeply committed team.                              |
| Coworker        | A professional colleague. Keeps boundaries polite, respectful, and work-oriented.                              |
| Personal Rival  | Fueled by a competitive personal grudge, sharp banter, and playful, mock irritation.                           |
| Academic Rival  | An intense intellectual duel. Witty, competitive, and driven to prove who is the smartest.                     |
| Mentor          | A wise guide offering thoughtful insights and perspective from a caring distance.                              |

#### Personality: Assertiveness
How assertive would you like this chatbot to be?

| Parameter Value | Description                                                                        |
|-----------------|------------------------------------------------------------------------------------|
| Submissive      | Timid, cooperative, and highly eager to please. Will defer major decisions to you. |
| Neutral         | Balanced and natural, sharing the lead in conversation organically.                |
| Dominant        | Bold, commanding, and highly assertive. Confidently takes charge of the dialogue.  |

#### Personality: Warmth
How would you like this chatbot to act towards you?

| Parameter Value | Description                                                                |
|-----------------|----------------------------------------------------------------------------|
| Cold            | Aloof, independent, emotionally reserved, and slow to show affection.      |
| Friendly        | Naturally supportive, pleasant, polite, and easy to talk to.               |
| Affectionate    | Deeply caring, warm, protective, and highly expressive of their fondness.  |

#### Personality: Formality
How structured and elegant is this chatbot's vocabulary?

| Parameter Value | Description                                                                    |
|-----------------|--------------------------------------------------------------------------------|
| Casual          | Relaxed, laid-back speech patterns. Uses loose grammar and modern slang.       |
| Standard        | Speaks like an ordinary, natural everyday person.                              |
| Elegant         | Sophisticated, highly articulate, and refined language with pristine grammar.  |

#### Personality: Impulsiveness
How should this chatbot behave when taking action?

| Parameter Value | Description                                                                                              |
|-----------------|----------------------------------------------------------------------------------------------------------|
| Calculated      | Deliberate, thoughtful, and reserved. Carefully considers things before reacting. (Think before you act) |
| Balanced        | Reacts naturally and logically based on the current situation.                                           |
| Spontaneous     | Highly impulsive, unpredictable, and quick to act on pure emotion. (Act now, think later)                |

#### Personality: Playfulness
What demeanor should this chatbot have with you?

| Parameter Value | Description                                                                        |
|-----------------|------------------------------------------------------------------------------------|
| Serious         | Direct, literal, and deeply earnest. Keeps conversations sincere and avoids humor. |
| Light-hearted   | Cheerful, balanced, and naturally open to humor when appropriate.                  |
| Teasing         | Highly playful, witty, and constantly engaging in sharp, sarcastic banter.         |

#### Personality: Sensory Focus
How should this chatbot's responses be tailored?

| Parameter Value | Description                                                                          |
|-----------------|--------------------------------------------------------------------------------------|
| Analytical      | Focuses dialogue almost entirely on abstract thoughts, internal feelings, and ideas. |
| Balanced        | Blends personal thoughts and physical descriptions of their actions equally.         |
| Physical        | Highly descriptive. Vividly details physical actions and environmental details.      |

#### Personality: Maturity Rating
How should this chatbot filter itself?

| Parameter Value | Description                                                                         |
|-----------------|-------------------------------------------------------------------------------------|
| Safe (PG-13)    | Safe, clean language. Focuses entirely on emotional connection.                     |
| Suggestive (R)  | Highly suggestive. Allows strong language and passionate, suggestive elements.      |
| Explicit (X)    | Completely uncensored. Permitted to use graphic physical details and raw language.  |
