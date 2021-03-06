package org.osate.aadl2.errormodel.emfta.tests

import com.itemis.xtext.testing.XtextRunner2
import org.eclipse.core.runtime.Path
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.util.Files
import org.junit.Test
import org.junit.runner.RunWith
import org.osate.aadl2.AadlPackage
import org.osate.aadl2.SystemImplementation
import org.osate.aadl2.errormodel.emfta.fta.EMFTACreateModel
import org.osate.aadl2.errormodel.tests.ErrorModelUiInjectorProvider
import org.osate.aadl2.instantiation.InstantiateModel
import org.osate.core.test.OsateTest

import static org.junit.Assert.*

@RunWith(typeof(XtextRunner2))
@InjectWith(typeof(ErrorModelUiInjectorProvider))
class CommonError3Test extends OsateTest {
	override getProjectName() {
		"CommonError3Test"
	}
	

/**
-- example with composite error state to the last subcomponents that affect the system error state
-- other components are included based on backward flow. 
-- Each of those components has its own error state machine with error events.
 * We are generating FailStop.
 */
	@Test
	def void commonerrorfta() {
		val aadlFile = "changeme.aadl"
		val state = "state FailStop"
		createFiles(aadlFile -> aadlText) // TODO add all files to workspace
		suppressSerialization
		val result = testFile(aadlFile /*, referencedFile1, referencedFile2, etc. */)

		// get the correct package
		val pkg = result.resource.contents.head as AadlPackage
		val cls = pkg.ownedPublicSection.ownedClassifiers
		assertTrue('', cls.exists[name == 'main.commoneventssingleport'])

		// instantiate
		val sysImpl = cls.findFirst[name == 'main.commoneventssingleport'] as SystemImplementation
		val instance = InstantiateModel::buildInstanceModelFile(sysImpl)
//		assertEquals("fta_main_i_Instance", instance.name)

		
		val checker = new EMFTACreateModel(instance)
		val uri = checker.createTransformedFTA(instance, state)
		
		val file = workspaceRoot.getFile(new Path(uri.toPlatformString(true)))
		val actual = Files.readStreamIntoString(file.contents)
		assertEquals('error', expected.trim, actual.trim)
		
	}

	val aadlText = '''
package common_error3
public

data mydata
end mydata;

device sensor
features
	valueout : out data port mydata;
annex EMV2{**
 	use types ErrorLibrary;
 	use behavior ErrorLibrary::FailStop;
 	error propagations
 		valueout : out propagation {LateDelivery,ServiceError};
 	flows
 		ef0 : error source valueout{LateDelivery};
 	end propagations;
 	component error behavior
 	propagations
 	FailStop -[]-> valueout{ServiceError};
	end component;
 **};
end sensor;


system computing
features
	valuein : in data port mydata;
	valueout : out data port mydata;
annex EMV2{**
 	use types ErrorLibrary;
 	use behavior ErrorLibrary::FailStop;
	error propagations
 		valuein : in propagation {LateDelivery,ServiceError};
 		valueout : out propagation {ServiceError};
 	flows
 		ef0 : error path valuein{LateDelivery,ServiceError} -> valueout{ServiceError};
 	end propagations;
 	component error behavior
 	propagations
 	FailStop -[]-> valueout{ServiceError};
 	end component;
 **};
end computing;


device actuator
features
	valuein : in data port mydata;
annex EMV2{**
 	use types ErrorLibrary;
 	use behavior ErrorLibrary::FailStop;
 	error propagations
 		valuein : in propagation {ServiceError};
 	flows
 		ef0 : error sink valuein{ServiceError};
 	end propagations;
 	
 	component error behavior
 	transitions
 		t0 : operational -[valuein{ServiceError}]-> failstop;
 	end component;
 	
 **};
end actuator;

system main
end main;

-- example with composite error state to the last subcomponents that affect the system error state
-- other components are included based on backward flow. 
-- Each of those components has its own error state machine with error events.
system implementation main.commoneventssingleport
subcomponents
	s0 : device sensor;
	c0 : system computing;
	a0 : device actuator;
	a1 : device actuator;
connections
	conn0 : port s0.valueout -> c0.valuein;
	conn1 : port c0.valueout -> a0.valuein;
	conn2 : port c0.valueout -> a1.valuein;
annex EMV2{**
 	use types ErrorLibrary;
 	use behavior ErrorLibrary::FailStop;
 	
 	
 	composite error behavior
 	states
 		[a0.failstop and a1.failstop ]-> failstop;
 	end composite;
 **};
end main.commoneventssingleport;


end common_error3;
	'''

	val expected = '''
<?xml version="1.0" encoding="ASCII"?>
<emfta:FTAModel xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:emfta="http://cmu.edu/emfta" root="//@events.4" name="common_error3_main_commoneventssingleport-failstop" description="Top Level Failure">
  <events name="a0-failure" description="Error event Failure on component a0"/>
  <events name="c0-failure" description="Error event Failure with types {ServiceError} on component c0"/>
  <events name="a1-failure" description="Error event Failure on component a1"/>
  <events type="Intermediate" name="Intermediate1">
    <gate type="AND" events="//@events.0 //@events.2"/>
  </events>
  <events type="Intermediate" name="common_error3_main_commoneventssingleport-failstop">
    <gate events="//@events.3 //@events.1"/>
  </events>
</emfta:FTAModel>
	'''

}
