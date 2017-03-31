package com.seanshubin.parser.domain

class CalculatorRuleLookup extends RuleLookup[Char] {
  private val rules: Seq[Rule[Char]] = Seq(
    OneOfRule(this, "expr", "num-plus-expr", "num"),
    SequenceRule(this, "num-plus-expr", "num", "plus", "expr"),
    OneOrMoreRule(this, "num", "digit"),
    ValueRule(this, "digit", '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'),
    ValueRule(this, "plus", '+')

  )
  private val rulesMap: Map[String, Rule[Char]] = rules.map(rule => (rule.thisRuleName, rule)).toMap

  override def lookupRuleByName(name: String): Rule[Char] = rulesMap(name)
}
