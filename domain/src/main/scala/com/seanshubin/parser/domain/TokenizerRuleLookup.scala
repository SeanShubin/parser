package com.seanshubin.parser.domain

class TokenizerRuleLookup extends RuleLookup[Char] {
  private val rules: Seq[Rule[Char]] = Seq(
    OneOfRule(this, "element", "number", "plus", "whitespaces"),
    OneOrMoreRule(this, "number", "digit"),
    ValueRule(this, "digit", '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'),
    ValueRule(this, "plus", '+'),
    OneOrMoreRule(this, "whitespaces", "whitespace"),
    ValueRule(this, "whitespace", '\n', '\r', '\t', ' ')
  )
  private val rulesMap: Map[String, Rule[Char]] = rules.map(rule => (rule.thisRuleName, rule)).toMap

  override def lookupRuleByName(name: String): Rule[Char] = rulesMap(name)
}
