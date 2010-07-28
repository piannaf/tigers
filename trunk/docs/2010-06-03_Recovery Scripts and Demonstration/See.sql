create view ViewContractor as select username, name, contact, phone, email, address from Contractor;

create view ViewLaboratory as select username, name, contact, phone, email, address from Laboratory;

create view ViewEnvironmentalOfficer as select username, name, phone, email from EnvironmentalOfficer;

create view ViewScreeningProgram as select id, start_date, complete, username from ScreeningProgram;

create view ViewSample as select id, date_taken, PH, EC, temperature, collar_depth, arsenic, grease, fluoride, chromium, username, tag from Sample;

create view ViewSampler as select tag, license, longtitude, latitude, collar_height, comp_screening_freq, purpose, contractor, waterbody, laboratory, depth_to_collar_screening_freq from Sampler;

create view ViewParameterNames as select parameter_id, name from ParameterNames;

create view ViewFrequencyItem as select frequency_id, parameter_id from FrequencyItem;

create view ViewScreeningItem as select program_id, tag, parameter_id from ScreeningItem;

create view ViewScreeningProgramSamplers as select id, tag, status from ScreeningProgramSamplers;

create view ViewParameterThresholds as select name, parameter_id, Min, Max from ParameterThresholds;

create view ViewSamplerMedia as select id, content, tag from SamplerMedia;

create view ViewScreeningFrequency as select id, description, frequency, tag from ScreeningFrequency;

create view ViewTigersUser as select username, password, activated, deleted from TigersUser;

create view ViewWaterbody as select name, type from Waterbody;