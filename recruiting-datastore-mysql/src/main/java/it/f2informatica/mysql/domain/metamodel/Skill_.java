/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.mysql.domain.metamodel;

import it.f2informatica.mysql.domain.Skill;
import it.f2informatica.mysql.domain.pk.SkillPK;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Skill.class)
public class Skill_ {

	public static volatile SingularAttribute<Skill, SkillPK> id;

}
