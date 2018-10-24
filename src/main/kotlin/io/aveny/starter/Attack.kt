package io.aveny.starter

data class Attack(val attackBonus : Int, val damageDie : Int, val damageDiceCount : Int, val damageBonus : Int)

data class AttackResult(val attack: Attack, val roll: Int, val ac: Int, val damage: Int)


fun makeAttack() : AttackResult {
  val atk = Attack(4, 6, 2, 2)
  return computeAttack(atk, 14)
}

fun computeAttack(attack : Attack, defenderAc: Int): AttackResult {
  var roll = roll(20)
  roll += attack.attackBonus
  if (defenderAc > roll) {
    return AttackResult(attack, roll, defenderAc, 0);
  }
  var dmg = attack.damageBonus
  for (i in 1..attack.damageDiceCount) {
    dmg += roll(attack.damageDie)
  }
  return AttackResult(attack, roll, defenderAc, dmg);
}


fun roll(die : Int) = (1..die).shuffled().last()
