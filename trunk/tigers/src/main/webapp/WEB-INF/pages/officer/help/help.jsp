<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="Online Help"/></title> 
<script type="text/javascript" src="<c:url value='/scripts/lightwindow.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/styles/lightwindow.css'/>" type="text/css" media="screen" />
<link rel="stylesheet" href="<c:url value='/styles/help.css'/>" type="text/css" /> 
</head> 
<h1>Online Help</h1>
<h3>
    Welcome to TiGERS Online Help. Here you will be able to learn all about the 
    functionality offered to your user role.
</h3>
<ul id="acc">
	<li id="gettingStarted">
		<h2 class="accordion_toggle">Getting Started</h2>
		<div class="acc-section">
			<div class="acc-content">
				<p>
					The 
					<a rel="Galleries[help]" class="lightwindow" 
						caption='The main menu tab' 
						href="/images/help/officer/welcome_menu.PNG">main menu tab</a>
					 gives you the ability to change your password and log out.
				</p>
				<p>
					Clicking on the Update Password menu item will bring up a screen 
					with the fields Password and Confirm Password. You must enter the 
					same password in both fields in order to update your password. Click
					save when you are ready to update the password.
				</p>
				<p>
					Clicking on the Log Out menu item will log you out of the 
					application. You will be presented again with the Login screen.
				</p>
			</div>
		</div>
	</li>

	<li id="administration">
		<h2 class="accordion_toggle">Administration of TiGERS Data</h2>
		<div class="acc-section">
			<div class="acc-content">
				<p>
					Environmental officers have the privilege of viewing and modifying 
					several key Data of TiGERS. This data consists of Sampler, Water Body,
					and Contractor data.
				</p>
				<ul class="acc" id="nested">
					<li id="addContractor">
						<h3>Adding and Updating a Contractor</h3>
						<div class="acc-section">
							<div class="acc-content">
								<p>
									Contractors collect samples for analysis and can upload 
									information about the sampling locations (samplers) assigned to
									them.
								</p>
								<p>
									To add a contractor to TiGERS, go to the 
									<a rel="Galleries[help]" class="lightwindow" 
									caption='The add contractor screen' 
									href="/images/help/officer/add_contractor.PNG">
										Add Contractor screen
									</a>.
									This can be found through the main menu as "Administrative Tasks
									&rarr; Add Contractor". After inputting relevant information, 
									click the Add button to commit the addition. The Add Contractor 
									form expects the following data (required fields are prefixed 
									with '*')
								</p>
								<ul>
									<li>
										*Username - Used for the contractor login<br />
										(at most 20 alphanumeric characters)
									</li>
									<li>
										*Company Name - Name of the company the contractor works for
										(at most 50 alphanumeric characters)
									</li>
									<li>
										*E-Mail - E-Mail address of the contractor<br />
										(at most 60 alphanumeric characters of the form 
										abc@domain.tld)
									</li>
									<li>
										Phone Number - Primary contact number of the contractor<br />
										(must be 10 digits)
									</li>
									<li>
										Address
										<ul>
											<li>
												*Address - Building number and street address where 
												contractor can be reached<br />
												(at most 100 alphanumeric characters)
											</li>
											<li>
												*City - City associated with above Address<br />
												(at most 50 alphanumeric characters)
											</li>
											<li>
												*State - State associated with the above City<br />
												(at most 100 alphanumeric characters)
											</li>
											<li>
						
												*Post Code - Post Code associated with the above 
												Address, City, and State<br />
												(at least 4 digits)
											</li>
											<li>
												*Country - The country associated with the above 
												State<br />
												(choose a country from the drop-down list)
											</li>
										</ul>
									</li>
								</ul>
								<p>
									After adding a contractor, you will be redirected to the 
										<a rel="Galleries[help]" class="lightwindow" 
										caption='The view contractors screen' 
										href="/images/help/officer/view_contractors.PNG">
											View Contractors screen
									</a> 
									as described below.
								</p>
								<p>
									To update a contractor, first visit the 
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The view contractors screen' 
										href="/images/help/officer/view_contractors.PNG">
											View Contractors screen
									</a>,
									search for the contractor you wish to modify, then click on the 
									appropriate row of the table. The update screen is the same 
									interface as the add screen described above but fields will be 
									populated with current values. Modify the fields you require 
									then click the Save button.
								</p>
								<p>
									If you wish to cancel an Add or Update, click the Cancel button 
									and you will be redirected to the main menu.
								</p>
							</div>
						</div>
					</li>
					
					<li id="addWaterBody">
						<h3>Adding and Updating and Deleting a Water Body</h3>
						<div class="acc-section">
							<div class="acc-content">
								<p>
									Water Bodies contain samplers which are used to collect samples for 
									analysis. They Have various parameter thresholds associated with 
									them.
								</p>
								<p>
									To add a water body to TiGERS, go to the  
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The add water body screen' 
										href="/images/help/officer/add_waterbody1.PNG">
											Add Water Body screen
									</a>. This 
									can be found through the main menu as "Administrative Tasks &rarr;
									Add Water Body". After inputting relevant information, click the Add 
									button to commit the addition. The Add Water Body form expects the 
									following data (required fields are prefixed with '*')
								</p>
								<ul>
									<li>
										*Username - Used for the contractor login <br />
										(at most 20 alphanumeric characters)
									</li>
									<li>
										*Type - Whether this is a ground water or surface water type<br />
										(choose a type from the drop down list)
									</li>
								</ul>
								<p>
									After adding a water body, you will be redirected to the  
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The view water bodies screen' 
										href="/images/help/officer/view_waterbodies.PNG">
											View Water Bodies screen
									</a> screen as described below.
								</p>
								<p>
									To update or delete a water body, first visit the   
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The view water bodies screen' 
										href="/images/help/officer/view_waterbodies.PNG">
											View Water Bodies screen
									</a>, search for the water body you wish to modify, then click on 
									the appropriate row of the table. The   
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The update waterbody screen' 
										href="/images/help/officer/update_waterbody.PNG">
											Update Water Body screen
									</a> is the same 
									interface as the add screen described above but it also contains 
									fields for Parameter Thresholds and all fields will be populated 
									with current values. Modify the fields you require then click the 
									Save button. The fields for Paramter Thresholds each have a Minimum 
									and Maximum value (all fields must contain positive numbers, Maximum 
									must be larger than Minimum, and all fields are required)
								</p>
								<ul>
									<li>
										*Temperature - The safe temperature range
										(must be a number of the form NNN.NN)
									</li>
									<li>
										*Arsenic - The safe arsenic range
										(must be a number of the form NN.NNN)
									</li>
									<li>
										*Oil & Grease - The safe oil and grease range
										(must be a number of the form NNN)
									</li>
									<li>
										*Depth to Collar - The regulated depth of water from collar
										(must be a number of the form NNNNN)
									</li>
									<li>
										*pH - The safe pH range
										(must be a number of the form NNN.NN)
									</li>
									<li>
										*Electrical Conductivity - The safe electrical conductivity range
										(must be a number of the form NNNNN)
									</li>
								</ul>
								<p>
									If you wish to delete the water body, click the Delete button.
								</p>
								<p>
									If you wish to cancel an Add or Update, click the Cancel button 
									and you will be redirected to the View Water Body screen.
								</p>
							</div>
						</div>
					</li>
					
					<li id="addSampler">
						<h3>Adding and Updating and Deleting a Sampler</h3>
						<div class="acc-section">
							<div class="acc-content">
								<p>
									Samplers are used to collect samples for analysis. They are 
									associated with a particular water body and contractor. Contractors 
									should acquire samples from each sampler at the specified sampling 
									frequency for each parameter associated with the sampler.
								</p>
								<p>
									To add a sampler to TiGERS, go to the   
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The add sampler screen' 
										href="/images/help/officer/add_sampler.PNG">
											Add Sampler screen
									</a>. This can 
									be found through the main menu as "Administrative Tasks &rarr; Add 
									Sampler". After inputting relevant information, click the Add button 
									to commit the addition. The Add Sampler form expects the following 
									data (required fields are prefixed with '*')
								</p>
								<ul>
									<li>
										*Tag - Unique ID 
										(must be 5 alphanumeric characters)
									</li>
									<li>
										* Latitude - Latitude of sampler's location
										(must be a number of the form NN.NNNNNN, in the range -90 to 90)
									</li>
									<li>
										* Longitude - Longitude of sampler's location
										(must be a number of the form NN.NNNNNN, in the range 0 to 180)
									</li>
									<li>
										Water Body - Primary contact number of the contractor
										(choose a water body from the drop down list)
									</li>
									<li>
										Purpose - The reason for adding this sampler
										(at most 50 alphanumeric characters)
									</li>
									<li>
										License - License number of sampler
										(required for samplers associated with ground water bodies, at 
										most 20 alphanumeric characters)
									</li>
									<li>
										Comprehensive Screening Frequency - How often this sampler 
										should be sampled for a comprehensive screening of parameters
										(choose a frequency from the drop down list)
									</li>
									<li>
										Laboratory - The laboratory tasked with testing samples from 
										this sampler
										(this field is not editable, it only exists for informational 
										purposes)
									</li>
									<li>
										Contractor - The contractor tasked with taking samples from this 
										sampler. 
										(choose a contractor from the drop down list)
									</li>
									<li>
										Collar Height - The height of the collar above sea level
										(required for samplers associated with ground water bodies, must
										be a number of the form NNNNN)
									</li>
									<li>
										Depth Screening Frequency - How often this sampler should 
										sampled for depth of the water below the collar height
										(required for samplers associated with ground water bodies, 
										choose a frequency from the drop down list)
									</li>
								</ul>
								<p>
									The latitude and longitude fields may be populated by dragging the 
									marker on the map. After adding a sampler, you will be redirected to 
									the View Samplers screen as described below.
								</p>
								<p>
									To update a sampler, first visit the View Samplers screen, search 
									for the water body associated with the sampler you wish to modify, 
									then click on the appropriate row of the table. The update screen is 
									the same interface as the add screen described above but fields will 
									be populated with current values (the additional Screening 
									Frequencies button is described below). Modify the fields you 
									require then click the Save button.
								</p>
								<p>
									If you wish to delete the sampler, click the Delete button.
								</p>
								<p>
									If you wish to cancel an Add or Update of sampler data, click the 
									Cancel button and you will be redirected to the View Samplers screen.
								</p>
								<p>
									If you wish to modify the screening frequencies associated with a 
									sampler's parameters, click the Screening Frequencies button. You 
									will be presented with a   
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The list of screening frequencies' 
										href="/images/help/officer/view_screening_frequencies.PNG">
											list of screening frequencies
									</a> for 
									the current sampler (which can be clicked to modify its data) and 
									the ability to   
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The view water bodies screen' 
										href="/images/help/officer/add_screening_frequencies.PNG">
											add new frequency data
									</a>. Screening frequency 
									data consists of the following data (required fields are prefixed 
									with '*')
								</p>
								<ul>
									<li>
										*Description - Describe the reason for this screening frequency
										(at most 20 alphanumeric characters)
									</li>
									<li>
										*Frequency - The frequency at which this screening should occur
										(choose a frequency from the drop down list)
									</li>
									<li>
										*Parameters - The parameters which must be sampled for this 
										screening
										(check the boxes next to the relevant parameters, at least one 
										must be selected)
									</li>
								</ul>
								<p>
									If you wish to cancel an Add or Update of screening frequency data, 
									click the Cancel button and you will be redirected to the    
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The list of screening frequencies' 
										href="/images/help/officer/view_screening_frequencies.PNG">
											Screening Frequencies screen
									</a>.
								</p>
								<p>
									It is also possible to add a Sampler via the    
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The interactive map' 
										href="/images/help/officer/map.PNG">
											Interactive Map
									</a>. See 
									the appropriate section below for more information.
								</p>
							</div>
						</div>
					</li>
					
					<li id="viewContractor">
						<h3>Viewing Contractor Information</h3>
						<div class="acc-section">
							<div class="acc-content">
								<p>
									To view all contractors, go to the 
									<a rel="Galleries[help]" class="lightwindow" 
									caption='The view contractors screen' 
									href="/images/help/officer/view_contractors.PNG">
										View Contractors screen
									</a> 
									. This can 
									be found through the main menu as "Administrative Tasks &rarr; View 
									Contractors".
								</p>
								<p>
									Here you will see a search field and a table with descriptive 
									headings. The search field can be used to narrow the range of 
									results shown in the table by listing contractors associated with 
									the given water body.
								</p>
							</div>
						</div>
					</li>
					
					<li id="viewWaterBody">
						<h3>Viewing Water Body Information</h3>
						<div class="acc-section">
							<div class="acc-content">
								<p>
									To view all water bodies, go to the    
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The view waterbodies screen' 
										href="/images/help/officer/view_waterbodies.PNG">
											View Waterbodies screen
									</a>. This 
									can be found through the main menu as "Administrative Tasks &rarr;
									View Water Bodies".
								</p>
								<p>
									Here you will see a search field and a table with descriptive 
									headings. The search field can be used to narrow the range of 
									results shown in the table by listing based on a specific water body.
								</p>
								</div>
						</div>
					</li>
					
					<li id="viewSampler">
						<h3>Viewing Sampler Information</h3>
						<div class="acc-section">
							<div class="acc-content">
								<p>
									To view all samplers, go to the    
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The view samplers screen' 
										href="/images/help/officer/view_samplers.PNG">
											View Samplers screen
									</a>. This can be 
									found through the main menu as "Administrative Tasks &rarr; View 
									Samplers".
								</p>
								<p>
									Here you will see a search field and a table with descriptive 
									headings. The search field can be used to narrow the range of 
									results shown in the table by listing samples associated with the 
									given water body.
								</p>
								<p>
									It is also possible to view Sampler Information via the     
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The interactive map' 
										href="/images/help/officer/map.PNG">
											Interactive Map
									</a>. See the appropriate section below for more information. 
								</p>
							</div>
						</div>
					</li>
					
					<li id="viewMedia">
						<h3>Viewing Sampler Media</h3>
						<div class="acc-section">
							<div class="acc-content">
								<p>
									To view all samplers, go to the    
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The view sampler media screen' 
										href="/images/help/officer/view_sampler_media1.PNG">
											View Sampler Media screen
									</a>. This can 
									be found through the main menu as "Administrative Tasks &rarr; View 
									Sampler Media".
								</p>
								<p>
									Here you will see list and map of samplers. Click on the relevant 
									sampler in the list or on the map. 
								</p>
								<p>
									You will then be directed to a    
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The list of sampler media files' 
										href="/images/help/officer/view_sampler_media2.PNG">
											list of sampler media files
									</a>. Click on 
									the name of the file you wish to view and it will be overlayed on 
									the screen. You may right-click and choose "Save Link As..." if you 
									wish to save the file locally.
								</p>
							</div>
						</div>
					</li>
					
					<li id="viewFrequency">
						<h3>Viewing Sampling Frequency Schedules</h3>
						<div class="acc-section">
							<div class="acc-content">
								<p>
									To view the sampling frequency schedules, go to the    
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The view samplers screen' 
										href="/images/help/officer/screening_frequency1.PNG">
											Screening Frequency Schedule screen
									</a>. 
									This can be found through the main menu as "Administrative Tasks
									&rarr; Schedule of Sampling Frequency".
								</p>
								<p>
									Here you will see a search field that will allow you to narrow the 
									range of sampling frequencies on the next page. You may also choose 
									a parameter by which to order the sampling frequencies. After 
									choosing the water body and ordering parameter, click the Search 
									button.
								</p>
								<p>
									The page will then be updated with a     
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The list of screening frequencies' 
										href="/images/help/officer/screening_frequency2.PNG">
											list of screening frequencies
									</a> 
									found for that water body.  Sampling parameters with a checkmark are
									associated with the frequency indicated in the Frequency column of 
									the table.
								</p>
							</div>
						</div>
					</li>
					
					<li id="interactiveMap">
						<h3>Using the Interactive Map</h3>
						<div class="acc-section">
							<div class="acc-content">
								<p>
									To view, update, or add sampler information via the map, go to the  
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The interactive map' 
										href="/images/help/officer/map.PNG">
											Interactive Map screen
									</a>. This can be found through the main menu as 
									"Interactive Map".
								</p>
								<p>
									Here you will see a satellite view of the Tiffany Gold Mine site 
									which will automatically zoom to show all samplers. On the right of 
									the map, there is a list of samplers which have been loaded from the 
									KML file and an information frame which shows non-empty parameters 
									associated with the currently selected sampler. 
								</p>
								<p>
									The map also allows you to pan, zoom and change map type. To pan, 
									you can either click and drag the mouse on the map or click the 
									arrows within the circle of the top-left control. To zoom, you may
									either use your mouse's scroll wheel or click the '+' or '-' buttons 
									on the zoom control, or click the zoom control itself on the left of 
									the map. To change map type, click on the type you wish to view 
									using map type control at the top-right. The default map type is 
									"Satellite", to view road information choose the "Map" type, to view 
									both satellite and road information, choose the "Hybrid" type, and 
									to view topographical information, choose the "Terrain" type.
								</p>
							
								<div id="mapView">
									<h4>Viewing Sampler Information via the Interactive Map</h4>
									<p>
										To download the KML file for use in Google Earth, click the link
										underneath the map. To select a sampler for viewing information, 
										place the mouse over the sampler in question either from its 
										icon on the map or its tag in the list. The information is 
										scrollable to view further parameter information which cannot 
										fit within the space.
									</p>
									<p>
										If you have reason to believe that sampler locations have been 
										modified, you may click the "Refresh Markers" link in the 
										bottom-right corner. This will reload an updated KML file based 
										on the current state of the data.
									</p>
								</div>
							
								<div id="mapUpdate">
									<h4>Updating Sampler Information via the Interactive Map</h4>
									<p>
										Clicking on a sampler icon from the map, or a tag in the list 
										will pop-up a comment box on the map indicating the latitude 
										(lat) and longitude (lng) of the sampler which was clicked along 
										with a link to edit the sampler information which will bring you 
										to the Update Sampler screen as described above.
									</p>
								</div>
							
								<div id="mapAdd">
								<h4>Adding Sampler Information via the Interactive Map</h4>
								<p>
									You will notice that moving the mouse cursor within the map
									will display the current latitude and longitude under the 
									cursor. This is most useful when you wish to add a sampler 
									via the map interface.
								</p>
								<p>
									To add a sampler at the indicated location, right click on 
									the map. A   
								<a rel="Galleries[help]" class="lightwindow" 
									caption='The interactive map context menu' 
									href="/images/help/officer/map2.PNG">
										context menu
								</a> will appear with the option to "Add 
									Sampler Here". Click on this option to be directed to the 
								<a rel="Galleries[help]" class="lightwindow" 
									caption='The add sampler screen' 
									href="/images/help/officer/add_sampler.PNG">
										Add Sampler screen
								</a> as described above.
								</p>
							</div>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</li>

	<li id="reports">
		<h2 class="accordion_toggle">Generating Reports</h2>
		<div class="acc-section">
			<div class="acc-content">
				<ul class="acc" id="nested2">
					<li id="reportExceed">
						<h3>Generating an Exceedance Report</h3>
						<div class="acc-section">
							<div class="acc-content">
								<p>
									To generate an exceedance report, go to the   
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The create exceedance report screen' 
										href="/images/help/officer/exceed_report1.PNG">
											Create Exceedance Report screen
									</a>. This can be found through the main menu as "Reports &rarr; 
									Create Exceedance Report".
								</p>
								<p>
									 Here you will see list and map of samplers. Click on the relevant 
									 sampler in the list or on the map. You will then be directed to the    
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The exceedance report screen' 
										href="/images/help/officer/exceed_report2.PNG">
											Exceedance Report screen
									</a>.
								</p>
								<p>
									 The table at the top of the screen defines the exceedance 
									 thresholds for parameters of the current sampler. Clicking on a row
									 of this table will direct you to the    
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The create exceedance report screen' 
										href="/images/help/officer/update_waterbody.PNG">
											Update Waterbody screen
									</a> as 
									 described in the previous section.
								</p>
								<p>
									The table at the bottom of the screen displays all samples that have
									exceeded the defined parameter thresholds. A sample that has 
									multiple parameters outside the exceedance threshold will appear in 
									multiple rows.
								</p>
							</div>
						</div>
					</li>
					
					<li id="reportAudit">
						<h3>Generating an Environmental Audit Report</h3>
						<div class="acc-section">
							<div class="acc-content">
								<p>
									To generate an environmental audit report, go to the    
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The create environmental audit report screen' 
										href="/images/help/officer/audit_report1.PNG">
											Create Environmental Audit Report screen
									</a>. This can be found through the 
									main menu as "Reports &rarr; Create Environmental Audit Report".
								</p>
								<p>
									 Here you will see list and map of samplers. Click on the relevant
									 sampler in the list or on the map. You will then be directed to the   
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The environmental audit report screen' 
										href="/images/help/officer/audit_report2.PNG">
											Environmental Audit Report screen
									</a>.
								</p>
								<p>
									 The table on this screen displays all samples associated with the 
									 current sampler and their parameter values.
								</p>
								<p>
									To generate a graph representation of the environmental audit 
									report, select a parameter to graph and click the Generate Graph 
									button. You will be directed to the    
									<a rel="Galleries[help]" class="lightwindow" 
										caption='The samples graph screen' 
										href="/images/help/officer/audit_report3.PNG">
											Samples Graph screen
									</a> where you 
									can see an interactive graph of the parameter chosen for all samples
									of the current sampler.  Place the mouse cursor over a circle on the 
									blue line to get information about the date the sample was taken and 
									the value obtained on that date. You may also select a different 
									parameter to view from this screen via the drop down box above the 
									graph.
								</p>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</li>

	<li id="screeningProgram">
		<h2 class="accordion_toggle">Executing a Screening Program</h2>
		<div class="acc-section">
			<div class="acc-content">
				<p>
					To execute a screening program, go to the    
						<a rel="Galleries[help]" class="lightwindow" 
							caption='The start new screening program screen' 
							href="/images/help/officer/screening_program1.PNG">
								Start New Screening Program screen
						</a>. This can be found through the main menu as "Executive Tasks 
					&rarr; Start New Screening Program"
				</p>
				<p>
					You must first select a water body to start the screening program. After 
					searching for a water body successfully, you will be presented with     
						<a rel="Galleries[help]" class="lightwindow" 
							caption='The start new screening program screen with next and cancel buttons' 
							href="/images/help/officer/screening_program2.PNG">
								Next and Cancel buttons
						</a>.  
				</p>
				<p>
					While executing a screening program, please do not press the back button 
					or navigate to another page unless you first press the Cancel button. 
					Pressing the Cancel button will bring you back to the     
						<a rel="Galleries[help]" class="lightwindow" 
							caption='The start new screening program screen' 
							href="/images/help/officer/welcome.PNG">
								Welcome screen
						</a> 
					where you can then navigate the application normally. Pressing the Prev 
					button will take you to the previous page viewed within the screening 
					program.
				</p>
				<p>
					 Pressing the Next button will take you     
						<a rel="Galleries[help]" class="lightwindow" 
							caption='One-by-one screening program' 
							href="/images/help/officer/screening_program3.PNG">
								one-by-one through each sampler
						</a>, 
					 showing you the sampling frequencies associated with that sampler and 
					 allow you to choose which parameters should be screened in the current 
					 screening program. Continue this process until you have reached the     
						<a rel="Galleries[help]" class="lightwindow" 
							caption='The screening program confirmation screen' 
							href="/images/help/officer/screening_program4.PNG">
								Screening Program Confirmation screen
						</a>.
				</p>
				<p>
					The      
						<a rel="Galleries[help]" class="lightwindow" 
							caption='The screening program confirmation screen' 
							href="/images/help/officer/screening_program4.PNG">
								Screening Program Confirmation screen
						</a> reports the samplers and 
					associated parameters that belong to the current screening program. To 
					confirm this screening program, press the Confirm button. After 
					confirming, you will be taken back to the Welcome screen. 
				</p>
				<p>
				Transparently, the application will send each contractor (based on the 
				samplers in the screening program) an email indicating which parameters 
				must be sampled at which samplers. If any of the email operations fails 
				you will get an      
					<a rel="Galleries[help]" class="lightwindow" 
						caption='The screening program email error' 
						href="/images/help/officer/screening_program5.PNG">
							error message
					</a> this could be because the email system is 
				not working or the email address does not exist.
			</p>
			</div>
		</div>
	</li>
</ul>
<script type="text/javascript" src="<c:url value='/scripts/help.js'/>"></script> 
 
<script type="text/javascript"> 
 
var parentAccordion=new TINY.accordion.slider("parentAccordion");
parentAccordion.init("acc","h2",1,0);
 
var nestedAccordion=new TINY.accordion.slider("nestedAccordion");
nestedAccordion.init("nested","h3",1,-1,"acc-selected");

var nested2=new TINY.accordion.slider("nestedAccordion");
nested2.init("nested2","h3",1,-1,"acc-selected");
 
</script> 
    
</script>

