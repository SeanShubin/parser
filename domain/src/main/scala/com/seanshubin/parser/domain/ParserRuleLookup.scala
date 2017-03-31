package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.CalculatorToken.CalculatorNumber

class ParserRuleLookup extends RuleLookup[CalculatorToken] {
  private val rules: Seq[Rule[CalculatorToken]] = Seq(
    SequenceRule(this, "expression", "num", "remaining-expr-or-end"),
    OneOfRule(this, "remaining-expr-or-end", "remaining-expr", "end"),
    SequenceRule(this, "remaining-expr", "plus", "num", "remaining-expr-or-end"),
    ValueTypeRule(this, "num", classOf[CalculatorNumber]),
    ValueRule(this, "plus", CalculatorToken.Plus),
    EndRule(this, "end")

  )
  private val rulesMap: Map[String, Rule[CalculatorToken]] = rules.map(rule => (rule.thisRuleName, rule)).toMap

  override def lookupRuleByName(name: String): Rule[CalculatorToken] = rulesMap(name)
}
