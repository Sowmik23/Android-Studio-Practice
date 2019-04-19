 
//adding back button to the tool bar



//OnCreate method er moddhe likhte hobe


	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	getSupportActionBar().setDisplayShowHomeEnabled(true);


//ebar Oncreate method er bhaire ei method ta likhte hobe


	@Override
	public boolean OnOptionsItemSelected(MenuItem item){
		
		if(item.getItemId()==android.R.id.home)
		{
			this.finish();
		}

		return super.OnOptionsItemSelected(item);
	}



// or another rule is change in Menifests files

set---->


	android:parentActivityName=".MainActivity"


//just example 


//for better help see the video 6.3 Anisul Android.
