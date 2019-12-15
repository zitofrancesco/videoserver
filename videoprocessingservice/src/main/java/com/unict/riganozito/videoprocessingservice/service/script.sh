ffmpeg -y -i ~/var/$1/$1.mp4 \
  -c:v libx264 -x264opts "keyint=24:min-keyint=24:no-scenecut" -r 24 \
  -c:a aac -strict -2 -b:a 128k \
  -bf 1 -b_strategy 0 -sc_threshold 0 -pix_fmt yuv420p \
  -map 0:v:0 -map 0:a:0 -map 0:v:0 -map 0:a:0 -map 0:v:0 -map 0:a:0 \
  -b:v:0 250k  -filter:v:0 "scale=-2:240" -profile:v:0 baseline \
  -b:v:1 750k  -filter:v:1 "scale=-2:480" -profile:v:1 main \
  -b:v:2 1500k -filter:v:2 "scale=-2:720" -profile:v:2 high \
  ~/var/$1/sample_dash.mp4

ffmpeg -y -re -i ~/var/$1/sample_dash.mp4 \
  -map 0 \
  -c:a aac -strict -2 -b:a 128k \
  -use_timeline 1 -use_template 1 -window_size 5 -adaptation_sets "id=0,streams=v id=1,streams=a" \
  -f dash ~/var/$1/$1.mpd
