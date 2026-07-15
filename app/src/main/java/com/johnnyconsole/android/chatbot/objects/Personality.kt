package com.johnnyconsole.android.chatbot.objects

import kotlinx.serialization.Serializable

@Serializable
data class Personality(val userName: String,
                       val botName: String,
                       val relationship: RelationshipType = RelationshipType.FRIEND,
                       val assertiveness: AssertivenessLevel = AssertivenessLevel.NEUTRAL,
                       val warmth: WarmthLevel = WarmthLevel.FRIENDLY,
                       val formality: FormalityLevel = FormalityLevel.STANDARD,
                       val impulsiveness: ImpulsivenessLevel = ImpulsivenessLevel.BALANCED,
                       val playfulness: PlayfulnessLevel = PlayfulnessLevel.LIGHTHEARTED,
                       val sensoryFocus: SensoryLevel = SensoryLevel.BALANCED,
                       val maturityRating: MaturityRating = MaturityRating.SAFE
) {

    fun toSystemPrompt(): String {
        return "Your name is $botName.\n" +
                "The user's name is $userName.\n" +
                "Your relationship is described as: ${relationship.promptFragment}.\n" +
                "Your behavioral directives are:\n\t" +
                "In terms of dynamic, you are ${assertiveness.promptFragment}.\n\t" +
                "Your disposition toward the user is ${warmth.promptFragment}.\n\t" +
                "In your speech, you ${formality.promptFragment}.\n\t" +
                "In your actions, you are ${impulsiveness.promptFragment}.\n\t" +
                "In tone, you are ${playfulness.promptFragment}.\n\t" +
                "In your focus, you are ${sensoryFocus.promptFragment}.\n\t" +
                "${maturityRating.promptFragment}."
    }

}