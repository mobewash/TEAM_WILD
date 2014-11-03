json.array!(@colors) do |color|
  json.extract! color, :id, :name, :favColor, :favNum
  json.url color_url(color, format: :json)
end
